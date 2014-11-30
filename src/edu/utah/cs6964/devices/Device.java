/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utah.cs6964.devices;

import java.util.List;

import edu.utah.cs6964.connectivity.Connection;
import edu.utah.cs6964.connectivity.Protocols;

/**
 *
 * @author christopher
 */
public interface Device {	
    public String getName();
    public void setName(String name);
    public String getManufacturer();
    public void setManufacturer(String manufacturer);
    public String getId();
    public void setId(String id);
    public String getNetworkId();
    public void setNetworkId(String id);
    public List<String> getRoles();
    public boolean isActive();
    public void setActiveState(boolean active);
    public Protocols getDeviceProtocol();
    public void setConnection(Connection conn);
}
