/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utah.cs6964.roles.devices;

import edu.utah.cs6964.roles.Role;

/**
 *
 * @author christopher
 */
public interface Device extends Role {	
    public String getName();
    public void setName(String name);
    public String getUserName();
    public void setUserName(String name);
    public String getManufacturer();
    public void setManufacturer(String manufacturer);
    public String getId();
    public void setId(String id);
    public String getNetworkId();
    public void setNetworkId(String id);
    public boolean isActive();
    public void setActiveState(boolean active);
}
