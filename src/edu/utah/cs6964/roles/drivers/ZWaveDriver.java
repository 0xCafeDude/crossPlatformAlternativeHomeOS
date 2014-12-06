/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utah.cs6964.roles.drivers;

import edu.utah.cs6964.exceptions.ModuleNotStartedException;
import edu.utah.cs6964.roles.devices.Device;

/**
 *
 * @author christopher
 */
public interface ZWaveDriver extends Driver {
	
    public boolean setLevel(Device d, short level) throws ModuleNotStartedException;
    
    public short getLevel(Device d) throws ModuleNotStartedException ;
    
    public int getMaxLevel(Device d) throws ModuleNotStartedException ;
    
    public int getMinLevel(Device d) throws ModuleNotStartedException;
}
