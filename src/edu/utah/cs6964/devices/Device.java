/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utah.cs6964.devices;

/**
 *
 * @author christopher
 */
public interface Device {
    public String getName();
    public String getManufacturer();
    public String getId();
    public void setId(String id);
}
