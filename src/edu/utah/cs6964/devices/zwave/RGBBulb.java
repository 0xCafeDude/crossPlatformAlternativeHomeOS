/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utah.cs6964.devices.zwave;

import java.util.List;

import edu.utah.cs6964.connectivity.Connection;
import edu.utah.cs6964.connectivity.Protocols;

/**
 *
 * @author christopher
 */
public class RGBBulb implements edu.utah.cs6964.devices.roles.lights.DimmingLightBulb {

    public RGBBulb(String id) {
        this.id = id;
        this.roles.add("DimmingLightBulb");
    }
    
    private String id,name, manufacturer;
    private List<String> roles;
    private String networkId;
    private boolean active;
    private Connection conn;
    
    @Override
    public String getId() {
        return this.id;
    }
    
    @Override
    public void setId(String id)
    {
        this.id = id;
    }
    
    @Override
    public String getManufacturer() {
        return manufacturer;
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void turnOn() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void turnOff() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public int getLevel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLevel(int level) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getMinLevel() {
        return 0;
    }
    
    @Override
    public int getMaxLevel() {
        return 100;
    }
    
    @Override
    public List<String> getRoles() {
    	return roles;
    }
    
    @Override
    public String getNetworkId() {
    	return networkId;
    }
    
    @Override
    public void setNetworkId(String networkId) {
    	this.networkId = networkId;
    }

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public void setActiveState(boolean active) {
		this.active = active;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((networkId == null) ? 0 : networkId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RGBBulb other = (RGBBulb) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (networkId == null) {
			if (other.networkId != null)
				return false;
		} else if (!networkId.equals(other.networkId))
			return false;
		return true;
	}

	@Override
	public Protocols getDeviceProtocol() {
		return Protocols.ZWAVE;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	@Override
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
}
