/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utah.cs6964.connectivity.protocols;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.zwave4j.Manager;
import org.zwave4j.NativeLibraryLoader;
import org.zwave4j.Notification;
import org.zwave4j.NotificationWatcher;
import org.zwave4j.Options;
import org.zwave4j.ValueGenre;
import org.zwave4j.ValueId;
import org.zwave4j.ValueType;
import org.zwave4j.ZWave4j;

import edu.utah.cs6964.api.Module;
import edu.utah.cs6964.devices.Device;
import edu.utah.cs6964.devices.DeviceFactory;
import edu.utah.cs6964.devices.zwave.DeviceFactoryZWave;
import edu.utah.cs6964.exceptions.ModuleNotStartedException;

/**
 *
 * @author christopher
 */
public class ZWave implements edu.utah.cs6964.connectivity.Connection, Module {
	
	private long homeId;
	private boolean READY= false, POLLING_ENABLED = false;
	private Manager manager;
	private DeviceFactory zwaveDeviceFactory = new DeviceFactoryZWave();
	private static ZWave zwaveInstance = null;
	private final String MODULE_NAME = "ZWave";
	private boolean startState = false;
	
	public static ZWave getInstance() {
		if (zwaveInstance == null) {
			zwaveInstance = new ZWave();
		}
		return zwaveInstance;
	}
	
	private ZWave() {
		
	}

    @Override
    public boolean sendData(Device d, byte[] bytes) throws ModuleNotStartedException {
    	if (!startState) {
    		throw new ModuleNotStartedException(getModuleName());
    	}
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public boolean setLevel(Device d, short level) throws ModuleNotStartedException {
    	if (!startState) {
    		throw new ModuleNotStartedException(getModuleName());
    	}
    	return manager.setValueAsByte(new ValueId(homeId, Short.parseShort(d.getId()), ValueGenre.USER, (short)38, (short)1,(short)0, ValueType.BYTE), level);
    }
    
    public short getLevel(Device d) throws ModuleNotStartedException {
    	if (!startState) {
    		throw new ModuleNotStartedException(getModuleName());
    	}
    	AtomicReference<Short> level = new AtomicReference<Short>();
    	manager.getValueAsByte(new ValueId(homeId, Short.parseShort(d.getId()), ValueGenre.USER, (short)38, (short)1,(short)0, ValueType.BYTE), level);
    	return level.get();
    }
    
    public int getMaxLevel(Device d) throws ModuleNotStartedException {
    	if (!startState) {
    		throw new ModuleNotStartedException(getModuleName());
    	}
    	return manager.getValueMax(new ValueId(homeId, Short.parseShort(d.getId()), ValueGenre.USER, (short)38, (short)1,(short)0, ValueType.BYTE));
    }
    
    public int getMinLevel(Device d) throws ModuleNotStartedException {
    	if (!startState) {
    		throw new ModuleNotStartedException(getModuleName());
    	}
    	return manager.getValueMin(new ValueId(homeId, Short.parseShort(d.getId()), ValueGenre.USER, (short)38, (short)1,(short)0, ValueType.BYTE));
    }

	@Override
	public void start() {
		if (!startState) {
			startState = true;
			NativeLibraryLoader.loadLibrary(ZWave4j.LIBRARY_NAME, ZWave4j.class);

	        final Options options = Options.create("/home/shivam/open-zwave2/config", "", "");
	        options.addOptionBool("ConsoleOutput", false);
	        options.lock();
	        
	        manager = Manager.create();
	        
	        final NotificationWatcher watcher = new NotificationWatcher() {
	            @Override
	            public void onNotification(Notification notification, Object context) {
	            	short nodeId;
	                switch (notification.getType()) {
	                    case DRIVER_READY:
	                        homeId = notification.getHomeId();
	                        break;
	                    case DRIVER_FAILED:
	                        System.out.println("ZWave Driver failed");
	                        break;
	                    case DRIVER_RESET:
	                        System.out.println("ZWave Driver reset");
	                        break;
	                    case ALL_NODES_QUERIED:
	                        manager.writeConfig(homeId);
	                        READY = true;
	                        break;
	                    case ALL_NODES_QUERIED_SOME_DEAD:
	                        manager.writeConfig(homeId);
	                        READY = true;
	                        break;
	                    case POLLING_ENABLED:
	                        POLLING_ENABLED = true;
	                        break;
	                    case POLLING_DISABLED:
	                    	POLLING_ENABLED = false;
	                        break;
	                    case NODE_NEW:
	                        nodeId = notification.getNodeId();
	                        addNode(nodeId);
	                        break;
	                    case NODE_ADDED:
	                    	nodeId = notification.getNodeId();
	                        addNode(nodeId);
	                        break;
	                    case NODE_REMOVED:
	                        System.out.println(String.format("Node removed\n" +
	                                "\tnode id: %d",
	                                notification.getNodeId()
	                        ));
	                        break;
	                }
	            }
	            
	            private void addNode(Short nodeId) {
	            	String manufacturerName, nodeName;
	            	manufacturerName = manager.getNodeManufacturerName(homeId, nodeId);
	                nodeName = manager.getNodeProductName(homeId, nodeId);
	                zwaveDeviceFactory.addDevice(new ZWaveNode(Short.toString(nodeId), nodeName, manufacturerName));
	            }
	        };
	        manager.addWatcher(watcher, null);
	        
	        final String controllerPort = "/dev/ttyUSB0";

	        manager.addDriver(controllerPort);
		}
	}

	@Override
	public void stop() {
		if(startState) {
			startState = false;
			manager = null;
		}
	}

	@Override
	public void serviceRegistered(List<String> roles) {
		// Will do nothing in this case
	}

	@Override
	public void serviceDeRegistered(List<String> roles) {
		// Will do nothing in this case
	}

	@Override
	public String getModuleName() {
		return MODULE_NAME;
	}
}
