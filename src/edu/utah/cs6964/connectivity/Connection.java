/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utah.cs6964.connectivity;

import edu.utah.cs6964.devices.Device;

/**
 *
 * @author christopher
 */
public interface Connection {
    public boolean sendData(Device d, byte[] bytes);
}
