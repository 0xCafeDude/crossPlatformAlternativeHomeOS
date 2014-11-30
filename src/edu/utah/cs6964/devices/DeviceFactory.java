package edu.utah.cs6964.devices;

import edu.utah.cs6964.connectivity.Node;

public interface DeviceFactory {
	public void addDevice(Node node);
	public void removeDevice(String nodeId);
	public void setActiveState(String nodeId, boolean active);
}
