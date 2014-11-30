package edu.utah.cs6964.connectivity.protocols;

import edu.utah.cs6964.connectivity.Node;

public class ZWaveNode implements Node {
	
	String id, name, manufacturer;
	
	public ZWaveNode() {
	}
	
	public ZWaveNode(String id, String name, String manufacturer) {
		this.id = id;
		this.name = name;
		this.manufacturer = manufacturer;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
}
