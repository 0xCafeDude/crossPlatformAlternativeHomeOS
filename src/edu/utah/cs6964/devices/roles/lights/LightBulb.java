/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utah.cs6964.devices.roles.lights;

/**
 *
 * @author christopher
 */
public interface LightBulb extends edu.utah.cs6964.devices.Device {
    public void turnOn();
    public void turnOff();
    
}
