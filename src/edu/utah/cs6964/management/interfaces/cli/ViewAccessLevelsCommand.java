/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utah.cs6964.management.interfaces.cli;

import edu.utah.cs6964.management.Core;
import java.util.Map;

/**
 *
 * @author christopher
 */
public class ViewAccessLevelsCommand implements Command {

    @Override
    public byte getMinimumAccessLevel() {
        return 6;
    }

    @Override
    public String getCommand() {
        return "levels";
    }

    @Override
    public String getDescription() {
        return "View Access Levels";
    }

    @Override
    public void doAction() {
        System.out.println("System Access Levels:");
            for(Map.Entry<Byte, String> level : Core.getInstance().getBackend().getAccessLevels().entrySet())
            {
                System.out.println("\t" + level.getKey() + ": " + level.getValue());
            }
    }
    
}
