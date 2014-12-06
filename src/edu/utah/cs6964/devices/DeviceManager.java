package edu.utah.cs6964.devices;

import java.util.ArrayList;
import java.util.List;

import edu.utah.cs6964.devices.zwave.RGBBulb;
import edu.utah.cs6964.drivers.Node;
import edu.utah.cs6964.management.ServiceManager;
import edu.utah.cs6964.roles.devices.Device;

public class DeviceManager {
	
	private static DeviceManager deviceManagerInstance = new DeviceManager();
	private List<Device> allDevices = new ArrayList<Device>();
	private ServiceManager serviceManager = ServiceManager.getInstance();
	
	public List<Device> getAllDevices() {
		return allDevices;
	}
	
	private DeviceManager() {
		
	}
	
	public static DeviceManager getInstance() {
		return deviceManagerInstance;
	}
	
	public void loadAllDevices() {
		// to read sql tables and stuff for now Hardcoding
		RGBBulb dev = new RGBBulb("2");
		dev.setActiveState(false);
		dev.setManufacturer("Zipato");
		dev.setName("RGBW Light Bulb");
		dev.setUserName("Zipato Bulb at the drawing room");
		dev.setNetworkId("3");
		allDevices.add(dev);
		serviceManager.addModule(dev);
	}
	
	public void createDevice(Node node) {
		
	}
	
	public void removeDevice(Node node) {
		
	}
}
