/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utah.cs6964.roles.devices.lights;

import edu.utah.cs6964.exceptions.ModuleNotStartedException;

/**
 *
 * @author christopher
 */
public interface DimmingLightBulb extends LightBulb {
    public int getLevel() throws ModuleNotStartedException;
    public void setLevel(int level) throws ModuleNotStartedException;
    public int getMinLevel() throws ModuleNotStartedException;
    public int getMaxLevel() throws ModuleNotStartedException;
}
