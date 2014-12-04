/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utah.cs6964.devices.roles.lights;

import edu.utah.cs6964.exceptions.ModuleNotStartedException;

/**
 *
 * @author christopher
 */
public interface LightBulb {
    public void turnOn() throws ModuleNotStartedException;
    public void turnOff() throws ModuleNotStartedException;
    
}
