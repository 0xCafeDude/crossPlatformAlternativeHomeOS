/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utah.cs6964.management.interfaces.cli;

/**
 *
 * @author christopher
 */
public interface Command {
    public byte getMinimumAccessLevel();
    public String getCommand();
    public String getDescription();
    public void doAction();
}
