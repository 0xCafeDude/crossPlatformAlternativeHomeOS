/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utah.cs6964.devices.lights;

/**
 *
 * @author christopher
 */
public interface DimmingLightBulb extends LightBulb {
    public int getLevel();
    public void setLevel(int level);
    public int getMinLevel();
    public int getMaxLevel();
}
