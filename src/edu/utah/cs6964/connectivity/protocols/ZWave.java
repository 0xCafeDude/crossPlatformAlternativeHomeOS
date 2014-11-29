/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utah.cs6964.connectivity.protocols;

import org.zwave4j.Manager;
import org.zwave4j.NativeLibraryLoader;
import org.zwave4j.Notification;
import org.zwave4j.NotificationWatcher;
import org.zwave4j.Options;
import org.zwave4j.ValueGenre;
import org.zwave4j.ValueId;
import org.zwave4j.ValueType;
import org.zwave4j.ZWave4j;

import edu.utah.cs6964.devices.Device;

/**
 *
 * @author christopher
 */
public class ZWave implements edu.utah.cs6964.connectivity.Connection {
	
	private long homeId;
	private boolean READY= false, POLLING_ENABLED = false;
	private Manager manager;
	
	public ZWave() {
		NativeLibraryLoader.loadLibrary(ZWave4j.LIBRARY_NAME, ZWave4j.class);

        final Options options = Options.create("/home/shivam/open-zwave2/config", "", "");
        options.addOptionBool("ConsoleOutput", false);
        options.lock();
        
        manager = Manager.create();
        
        final NotificationWatcher watcher = new NotificationWatcher() {
            @Override
            public void onNotification(Notification notification, Object context) {
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
                        System.out.println(String.format("Node new\n" +
                                "\tnode id: %d",
                                notification.getNodeId()
                        ));
                        break;
                    case NODE_ADDED:
                        System.out.println(String.format("Node added\n" +
                                "\tnode id: %d",
                                notification.getNodeId()
                        ));
                        break;
                    case NODE_REMOVED:
                        System.out.println(String.format("Node removed\n" +
                                "\tnode id: %d",
                                notification.getNodeId()
                        ));
                        break;
                }
            }
        };
        manager.addWatcher(watcher, null);
        
        final String controllerPort = "/dev/ttyUSB0";

        manager.addDriver(controllerPort);
	}

    @Override
    public boolean sendData(Device d, byte[] bytes) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public boolean setLevel(Device d, short level) {
    	return manager.setValueAsByte(new ValueId(homeId, Short.parseShort(d.getId()), ValueGenre.USER, (short)38, (short)1,(short)0, ValueType.BYTE), level);
    }
    
}
