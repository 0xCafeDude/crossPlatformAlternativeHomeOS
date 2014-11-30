package edu.utah.cs6964.management.devices;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.utah.cs6964.devices.Device;

public class SystemDevices {
	private List<Device> deviceList = new ArrayList<Device>();
	private static SystemDevices systemDevices = null;
	
	private SystemDevices () {
		
	}
	
	public static SystemDevices getInstance() {
		if (systemDevices == null) {
			systemDevices = new SystemDevices();
		}
		return systemDevices;
	}
	
	
	public void addDevice(Device d) {
		deviceList.add(d);
	}
	
	public void removeDevice(Device d) {
		deviceList.remove(d);
	}
	
	public Device getDevice(String id){
		for (Device d : deviceList) {
			if (d.getId().equals(id)) {
				return d;
			}
		}
		return null;
	}
	
	public Iterator<Device> getDeviceIterator() {
		return deviceList.iterator();
	}
	
	public int getDeviceCount () {
		return deviceList.size();
	}
}
