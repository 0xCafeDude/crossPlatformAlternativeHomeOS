/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utah.cs6964.drivers.zwave;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

import edu.utah.cs6964.devices.DeviceManager;
import edu.utah.cs6964.drivers.Node;
import edu.utah.cs6964.exceptions.ModuleNotStartedException;
import edu.utah.cs6964.modules.Module;
import edu.utah.cs6964.roles.Role;
import edu.utah.cs6964.roles.devices.Device;
import edu.utah.cs6964.roles.drivers.ZWaveDriver;

/**
 *
 * @author christopher
 */
public class ZWave implements Module, ZWaveDriver {
	
	private long homeId;
	private boolean READY= false, POLLING_ENABLED = false;
	private Manager manager;
	private DeviceManager deviceManager = DeviceManager.getInstance();
	private static ZWave zwaveInstance = new ZWave();
	private boolean startState = false;
	private String id;
	private List<String> offeredRoles = new ArrayList<String>();
	
	public static ZWave getInstance() {
		return zwaveInstance;
	}
	
	private ZWave() {
		offeredRoles.add("ZWaveDriver");
	}

    @Override
    public boolean sendData(Device d, byte[] bytes) throws ModuleNotStartedException {
    	if (!startState) {
    		throw new ModuleNotStartedException(getModuleId());
    	}
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public boolean setLevel(Device d, short level) throws ModuleNotStartedException {
    	if (!startState) {
    		throw new ModuleNotStartedException(getModuleId());
    	}
    	return manager.setValueAsByte(new ValueId(homeId, Short.parseShort(d.getId()), ValueGenre.USER, (short)38, (short)1,(short)0, ValueType.BYTE), level);
    }
    
    public short getLevel(Device d) throws ModuleNotStartedException {
    	if (!startState) {
    		throw new ModuleNotStartedException(getModuleId());
    	}
    	AtomicReference<Short> level = new AtomicReference<Short>();
    	manager.getValueAsByte(new ValueId(homeId, Short.parseShort(d.getId()), ValueGenre.USER, (short)38, (short)1,(short)0, ValueType.BYTE), level);
    	return level.get();
    }
    
    public int getMaxLevel(Device d) throws ModuleNotStartedException {
    	if (!startState) {
    		throw new ModuleNotStartedException(getModuleId());
    	}
    	return manager.getValueMax(new ValueId(homeId, Short.parseShort(d.getId()), ValueGenre.USER, (short)38, (short)1,(short)0, ValueType.BYTE));
    }
    
    public int getMinLevel(Device d) throws ModuleNotStartedException {
    	if (!startState) {
    		throw new ModuleNotStartedException(getModuleId());
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
	                        createNode(nodeId);
	                        break;
	                    case NODE_REMOVED:
	                        System.out.println(String.format("Node removed\n" +
	                                "\tnode id: %d",
	                                notification.getNodeId()
	                        ));
	                        break;
	                }
	            }
	            
	            private void createNode(Short nodeId) {
	            	String manufacturerName, nodeName;
	            	manufacturerName = manager.getNodeManufacturerName(homeId, nodeId);
	                nodeName = manager.getNodeProductName(homeId, nodeId);
	                deviceManager.createDevice(new Node(Short.toString(nodeId), nodeName, manufacturerName));
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
	public String getModuleId() {
		return getId();
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public List<String> getOfferedRoles() {
		return offeredRoles;
	}

	@Override
	public List<String> getRequiredRoles() {
		return null;
	}

	@Override
	public void setRequiredRoles(Map<String, Role> roleMap) {
		
	}
}
