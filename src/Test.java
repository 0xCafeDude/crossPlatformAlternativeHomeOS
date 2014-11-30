/*
 * Copyright (c) 2013 Alexander Zagumennikov
 *
 * SOFTWARE NOTICE AND LICENSE
 *
 * This file is part of ZWave4J.
 *
 * ZWave4J is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ZWave4J is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ZWave4J.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

/**
 * @author zagumennikov
 */
public class Test {

    private static long homeId;
    private static boolean ready = false;

    public static void main(String[] args) throws IOException {
        NativeLibraryLoader.loadLibrary(ZWave4j.LIBRARY_NAME, ZWave4j.class);

        final Options options = Options.create("/home/shivam/open-zwave2/config", "", "");
        options.addOptionBool("ConsoleOutput", false);
        options.lock();

        final Manager manager = Manager.create();

        final NotificationWatcher watcher = new NotificationWatcher() {
            @Override
            public void onNotification(Notification notification, Object context) {
                switch (notification.getType()) {
                    case DRIVER_READY:
                        System.out.println(String.format("Driver ready\n" +
                                "\thome id: %d",
                                notification.getHomeId()
                        ));
                        homeId = notification.getHomeId();
                        break;
                    case DRIVER_FAILED:
                        System.out.println("Driver failed");
                        break;
                    case DRIVER_RESET:
                        System.out.println("Driver reset");
                        break;
                    case AWAKE_NODES_QUERIED:
                        System.out.println("Awake nodes queried");
                        break;
                    case ALL_NODES_QUERIED:
                        System.out.println("All nodes queried");
                        manager.writeConfig(homeId);
                        ready = true;
                        break;
                    case ALL_NODES_QUERIED_SOME_DEAD:
                        System.out.println("All nodes queried some dead");
                        manager.writeConfig(homeId);
                        ready = true;
                        break;
                    case POLLING_ENABLED:
                        System.out.println("Polling enabled");
                        break;
                    case POLLING_DISABLED:
                        System.out.println("Polling disabled");
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
                    case ESSENTIAL_NODE_QUERIES_COMPLETE:
                        System.out.println(String.format("Node essential queries complete\n" +
                                "\tnode id: %d",
                                notification.getNodeId()
                        ));
                        break;
                    case NODE_QUERIES_COMPLETE:
                        System.out.println(String.format("Node queries complete\n" +
                                "\tnode id: %d",
                                notification.getNodeId()
                        ));
                        break;
                    case NODE_EVENT:
                        System.out.println(String.format("Node event\n" +
                                "\tnode id: %d\n" +
                                "\tevent id: %d",
                                notification.getNodeId(),
                                notification.getEvent()
                        ));
                        break;
                    case NODE_NAMING:
                        System.out.println(String.format("Node naming\n" +
                                "\tnode id: %d",
                                notification.getNodeId()
                        ));
                        break;
                    case NODE_PROTOCOL_INFO:
                        System.out.println(String.format("Node protocol info\n" +
                                "\tnode id: %d\n" +
                                "\ttype: %s",
                                notification.getNodeId(),
                                manager.getNodeType(notification.getHomeId(), notification.getNodeId())
                        ));
                        break;
                    case VALUE_ADDED:
                        System.out.println(String.format("Value added\n" +
                                "\tnode id: %d\n" +
                                "\tcommand class: %d\n" +
                                "\tinstance: %d\n" +
                                "\tindex: %d\n" +
                                "\tgenre: %s\n" +
                                "\ttype: %s\n" +
                                "\tlabel: %s\n" +
                                "\tvalue: %s",
                                notification.getNodeId(),
                                notification.getValueId().getCommandClassId(),
                                notification.getValueId().getInstance(),
                                notification.getValueId().getIndex(),
                                notification.getValueId().getGenre().name(),
                                notification.getValueId().getType().name(),
                                manager.getValueLabel(notification.getValueId()),
                                getValue(notification.getValueId())
                        ));
                        break;
                    case VALUE_REMOVED:
                        System.out.println(String.format("Value removed\n" +
                                "\tnode id: %d\n" +
                                "\tcommand class: %d\n" +
                                "\tinstance: %d\n" +
                                "\tindex: %d",
                                notification.getNodeId(),
                                notification.getValueId().getCommandClassId(),
                                notification.getValueId().getInstance(),
                                notification.getValueId().getIndex()
                        ));
                        break;
                    case VALUE_CHANGED:
                        System.out.println(String.format("Value changed\n" +
                                "\tnode id: %d\n" +
                                "\tcommand class: %d\n" +
                                "\tinstance: %d\n" +
                                "\tindex: %d\n" +
                                "\tvalue: %s",
                                notification.getNodeId(),
                                notification.getValueId().getCommandClassId(),
                                notification.getValueId().getInstance(),
                                notification.getValueId().getIndex(),
                                getValue(notification.getValueId())
                        ));
                        break;
                    case VALUE_REFRESHED:
                        System.out.println(String.format("Value refreshed\n" +
                                "\tnode id: %d\n" +
                                "\tcommand class: %d\n" +
                                "\tinstance: %d\n" +
                                "\tindex: %d" +
                                "\tvalue: %s",
                                notification.getNodeId(),
                                notification.getValueId().getCommandClassId(),
                                notification.getValueId().getInstance(),
                                notification.getValueId().getIndex(),
                                getValue(notification.getValueId())
                        ));
                        break;
                    case GROUP:
                        System.out.println(String.format("Group\n" +
                                "\tnode id: %d\n" +
                                "\tgroup id: %d",
                                notification.getNodeId(),
                                notification.getGroupIdx()
                        ));
                        break;

                    case SCENE_EVENT:
                        System.out.println(String.format("Scene event\n" +
                                "\tscene id: %d",
                                notification.getSceneId()
                        ));
                        break;
                    case CREATE_BUTTON:
                        System.out.println(String.format("Button create\n" +
                                "\tbutton id: %d",
                                notification.getButtonId()
                        ));
                        break;
                    case DELETE_BUTTON:
                        System.out.println(String.format("Button delete\n" +
                                "\tbutton id: %d",
                                notification.getButtonId()
                        ));
                        break;
                    case BUTTON_ON:
                        System.out.println(String.format("Button on\n" +
                                "\tbutton id: %d",
                                notification.getButtonId()
                        ));
                        break;
                    case BUTTON_OFF:
                        System.out.println(String.format("Button off\n" +
                                "\tbutton id: %d",
                                notification.getButtonId()
                        ));
                        break;
                    case NOTIFICATION:
                        System.out.println("Notification");
                        break;
                    default:
                        System.out.println(notification.getType().name());
                        break;
                }
            }
        };
        manager.addWatcher(watcher, null);

        final String controllerPort = "/dev/ttyUSB0";

        manager.addDriver(controllerPort);

        final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String line;
        do {
            line = br.readLine();
            if (!ready || line == null) {
                continue;
            }

            switch (line) {
                case "on":
                    manager.switchAllOn(homeId);
                    break;
                case "off":
                    manager.switchAllOff(homeId);
                    break;
                case "test":
                	short light = 3;
                	/*System.out.println(manager.getValueHelp(new ValueId(homeId, light, ValueGenre.SYSTEM, (short)115, (short)1, (short)0, ValueType.LIST)));
                	System.out.println(manager.getValueMax(new ValueId(homeId, light, ValueGenre.SYSTEM, (short)115, (short)1, (short)0, ValueType.LIST)));
                	System.out.println(manager.getValueMin(new ValueId(homeId, light, ValueGenre.SYSTEM, (short)115, (short)1, (short)0, ValueType.LIST)));
                	System.out.println(manager.getValueHelp(new ValueId(homeId, light, ValueGenre.SYSTEM, (short)115, (short)1, (short)2, ValueType.LIST)));
                	System.out.println(manager.getValueMax(new ValueId(homeId, light, ValueGenre.SYSTEM, (short)115, (short)1, (short)2, ValueType.LIST)));
                	System.out.println(manager.getValueMin(new ValueId(homeId, light, ValueGenre.SYSTEM, (short)115, (short)1, (short)2, ValueType.LIST)));*/
            		System.out.println(manager.getValueHelp(new ValueId(homeId, light, ValueGenre.USER, (short)38, (short)1, (short)0, ValueType.BYTE)));
            		System.out.println(manager.getValueMax(new ValueId(homeId, light, ValueGenre.USER, (short)38, (short)1, (short)0, ValueType.BYTE)));
            		System.out.println(manager.getValueMin(new ValueId(homeId, light, ValueGenre.USER, (short)38, (short)1, (short)0, ValueType.BYTE)));
            		System.out.println(" node name "+manager.getNodeProductName(homeId, light));
                	while (true) {
                		System.out.println("Enter 1 to turn on 0 to turn off");
                		line = br.readLine();
                		short onOff = Short.parseShort(line);
                		/*System.out.println("Play with instance 2");
                		line = br.readLine();
                		short instance2 = Short.parseShort(line);*/
                		System.out.println("Enter color value between 1 to 100");
                		line = br.readLine();
                		short color = Short.parseShort(line);
                		System.out.println("Hack the color");
                		line = br.readLine();
                		short hackColor = Short.parseShort(line);
                		/*System.out.println("Enter powerlevel");
                		line = br.readLine();
                		short powerlevel = Short.parseShort(line);*/
                    	//System.out.println(manager.getNodeManufacturerName(homeId, light));
                    	//manager.setNodeOn(homeId, light);
                    	//System.out.println(manager.isNodeAwake(homeId, light));
                		// default was null
                		/*if (powerlevel == 1){
                			manager.pressButton(new ValueId(homeId, light, ValueGenre.SYSTEM, (short)115, (short)1, (short)2, ValueType.BUTTON));
                		}
                		if (powerlevel == 2) {
                			manager.releaseButton(new ValueId(homeId, light, ValueGenre.SYSTEM, (short)115, (short)1, (short)2, ValueType.BUTTON));
                		}*/
                    	// default value was 0
                		if (onOff != -1)
                			manager.setValueAsByte(new ValueId(homeId, light, ValueGenre.USER, (short)38, (short)1,(short)0, ValueType.BYTE), onOff);
                		if (hackColor >= 0 && hackColor <= 4)
                			manager.setValueAsByte(new ValueId(homeId, light, ValueGenre.USER, (short)38, (short)1,(short)9, ValueType.BYTE), hackColor);
                		/*if (instance2 != -1) {
                			manager.setValueAsByte(new ValueId(homeId, light, ValueGenre.USER, (short)38, (short)2,(short)0, ValueType.BYTE), instance2);	
                		}*/
                    	//default value was 50
                		if (color != -1)
                			manager.setValueAsByte(new ValueId(homeId, light, ValueGenre.CONFIG, (short)112, (short)1,(short)1, ValueType.BYTE), color);
                    	System.out.println("Enter q to quit");
                    	line = br.readLine();
                    	if (line != null && line.equals("q")) {
                			break;
                		}
                	}
                	break;
            }
        } while(line != null && !line.equals("q"));


        br.close();

        manager.removeWatcher(watcher, null);
        manager.removeDriver(controllerPort);
        Manager.destroy();
	    Options.destroy();
    }

    private static Object getValue(ValueId valueId) {
        switch (valueId.getType()) {
            case BOOL:
                AtomicReference<Boolean> b = new AtomicReference<>();
                Manager.get().getValueAsBool(valueId, b);
                return b.get();
            case BYTE:
                AtomicReference<Short> bb = new AtomicReference<>();
                Manager.get().getValueAsByte(valueId, bb);
                return bb.get();
            case DECIMAL:
                AtomicReference<Float> f = new AtomicReference<>();
                Manager.get().getValueAsFloat(valueId, f);
                return f.get();
            case INT:
                AtomicReference<Integer> i = new AtomicReference<>();
                Manager.get().getValueAsInt(valueId, i);
                return i.get();
            case LIST:
                return null;
            case SCHEDULE:
                return null;
            case SHORT:
                AtomicReference<Short> s = new AtomicReference<>();
                Manager.get().getValueAsShort(valueId, s);
                return s.get();
            case STRING:
                AtomicReference<String> ss = new AtomicReference<>();
                Manager.get().getValueAsString(valueId, ss);
                return ss.get();
            case BUTTON:
                return null;
            case RAW:
                AtomicReference<short[]> sss = new AtomicReference<>();
                Manager.get().getValueAsRaw(valueId, sss);
                return sss.get();
            default:
                return null;
        }
    }
}
