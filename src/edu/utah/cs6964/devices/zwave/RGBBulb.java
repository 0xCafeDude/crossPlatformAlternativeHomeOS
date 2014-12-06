/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utah.cs6964.devices.zwave;

import java.util.ArrayList;
import java.util.List;

import edu.utah.cs6964.drivers.zwave.ZWave;
import edu.utah.cs6964.exceptions.ModuleNotStartedException;
import edu.utah.cs6964.management.ServiceManager;
import edu.utah.cs6964.modules.Module;

/**
 *
 * @author christopher
 */
public class RGBBulb implements edu.utah.cs6964.roles.devices.lights.DimmingLightBulb, Module {

    public RGBBulb(String id) {
        this.id = id;
        this.offferedRoles.add("DimmingLightBulb");
        requiredRoles.add("ZWaveDriver");
    }
    
    private String id,name, manufacturer;
    private List<String> offferedRoles = new ArrayList<String>();
    private List<String> requiredRoles = new ArrayList<String>();
    private String networkId, userName;
    private boolean active;
    private ZWave conn;
    private boolean started = false;
    
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
    public String getManufacturer(){
        return manufacturer;
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void turnOn() throws ModuleNotStartedException {
    	if (!started) {
    		throw new ModuleNotStartedException(getModuleId());
    	}
    	conn.setLevel(this, (short)20);
    }

    @Override
    public void turnOff() throws ModuleNotStartedException {
    	if (!started) {
    		throw new ModuleNotStartedException(getModuleId());
    	}
    	conn.setLevel(this, (short)0);
    }
    
    @Override
    public int getLevel() throws ModuleNotStartedException {
    	if (!started) {
    		throw new ModuleNotStartedException(getModuleId());
    	}
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLevel(int level) throws ModuleNotStartedException {
    	if (!started) {
    		throw new ModuleNotStartedException(getModuleId());
    	}
    	conn.setLevel(this, (short)level);
    }

    @Override
    public int getMinLevel() throws ModuleNotStartedException {
    	if (!started) {
    		throw new ModuleNotStartedException(getModuleId());
    	}
        return conn.getMinLevel(this);
    }
    
    @Override
    public int getMaxLevel() throws ModuleNotStartedException {
    	if (!started) {
    		throw new ModuleNotStartedException(getModuleId());
    	}
        return conn.getMaxLevel(this);
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
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setManufacturer(String manufacturer)  {
		this.manufacturer = manufacturer;
	}

	@Override
	public void start() {
		if (getRequiredRolesFromServiceManager()) {
			started = true;
		}
	}

	@Override
	public void stop() {
		started = false;
	}

	@Override
	public void serviceRegistered(List<String> roles) {
	}

	@Override
	public void serviceDeRegistered(List<String> roles) {
	}

	@Override
	public String getModuleId() {
		return getId();
	}

	@Override
	public List<String> getOfferedRoles() {
		return offferedRoles;
	}

	private boolean getRequiredRolesFromServiceManager() {
		this.conn = (ZWave)ServiceManager.getInstance().getRole(this, requiredRoles.get(0));
		if (this.conn == null) {
			return false;
		}
		return true;
	}

	@Override
	public String getUserName() {
		return userName;
	}

	@Override
	public void setUserName(String name) {
		this.userName = name;
	}
}
