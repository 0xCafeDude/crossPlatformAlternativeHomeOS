/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utah.cs6964.roles.devices.lights;

import edu.utah.cs6964.exceptions.ModuleNotStartedException;
import edu.utah.cs6964.roles.devices.Device;

/**
 *
 * @author christopher
 */
public interface LightBulb extends Device {
    public void turnOn() throws ModuleNotStartedException;
    public void turnOff() throws ModuleNotStartedException;
    
}
