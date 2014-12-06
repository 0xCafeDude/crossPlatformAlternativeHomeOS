package edu.utah.cs6964.drivers;


public class Node {
	
	String id, name, manufacturer;
	
	public Node() {
	}
	
	public Node(String id, String name, String manufacturer) {
		this.id = id;
		this.name = name;
		this.manufacturer = manufacturer;
	}

	public String getId() {
		return this.id;
	}

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
