package edu.utah.cs6964.devices.zwave;

import java.util.Iterator;

import edu.utah.cs6964.connectivity.Connection;
import edu.utah.cs6964.connectivity.Node;
import edu.utah.cs6964.connectivity.Protocols;
import edu.utah.cs6964.connectivity.protocols.ZWave;
import edu.utah.cs6964.connectivity.protocols.ZWaveNode;
import edu.utah.cs6964.devices.Device;
import edu.utah.cs6964.devices.DeviceFactory;
import edu.utah.cs6964.management.devices.SystemDevices;

public class DeviceFactoryZWave implements DeviceFactory {
	
	private SystemDevices systemDevices = SystemDevices.getInstance();
	private Connection conn = ZWave.getInstance();
	
	@Override
	public void addDevice(Node node) {
		Device dev = findDevice(node.getId());
		ZWaveNode zNode = (ZWaveNode)node;
		if (dev == null) {
			int id = systemDevices.getDeviceCount()+1;
			String name = ((ZWaveNode)node).getName();
			switch (name) {
			case "RGBW LED Bulb":
				dev = new RGBBulb(Integer.toString(id));
				dev.setActiveState(true);
				dev.setNetworkId(node.getId());
				dev.setConnection(conn);
				dev.setManufacturer(zNode.getManufacturer());
				dev.setName(zNode.getName());
				break;
			}
		} else {	// it's an update operation.
			if (zNode.getName() != null && !zNode.getName().equals("")) {
				dev.setName(zNode.getName());
			}
			if (zNode.getManufacturer() != null && !zNode.getManufacturer().equals("")) {
				dev.setManufacturer(zNode.getManufacturer());
			}
		}
	}

	@Override
	public void removeDevice(String nodeId) {
		Device dev = findDevice(nodeId);
		if (dev != null) {
			systemDevices.removeDevice(dev);
		}
	}

	@Override
	public void setActiveState(String nodeId, boolean active) {
		Device dev = findDevice(nodeId);
		if (dev != null) {
			dev.setActiveState(active);
		}
	}
	
	private Device findDevice(String nodeId) {
		Iterator<Device> it = systemDevices.getDeviceIterator();
		while (it.hasNext()) {
			Device dev = it.next();
			if (dev.getDeviceProtocol() == Protocols.ZWAVE && dev.getNetworkId().equals(nodeId) ) {
				return dev;
			}
		}
		return null;
	}
}
