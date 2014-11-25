/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utah.cs6964.drivers.zipato;

/**
 *
 * @author christopher
 */
public class RGBBulb implements edu.utah.cs6964.devices.lights.DimmingLightBulb {

    public RGBBulb(String id) {
        this.id = id;
    }
    
    private String id;
    
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
        return "Zipato";
    }
    
    @Override
    public String getName() {
        return "RGB Bulb";
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

}
