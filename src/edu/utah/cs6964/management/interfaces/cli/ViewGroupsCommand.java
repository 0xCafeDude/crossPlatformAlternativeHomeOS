/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utah.cs6964.management.interfaces.cli;

import edu.utah.cs6964.management.Core;
import edu.utah.cs6964.management.access.Group;

/**
 *
 * @author christopher
 */
public class ViewGroupsCommand implements Command {

    @Override
    public byte getMinimumAccessLevel() {
        return 4;
    }

    @Override
    public String getCommand() {
        return "groups";
    }

    @Override
    public String getDescription() {
        return "View Groups";
    }

    @Override
    public void doAction() {
        System.out.println("Groups:");
            for(Group g : Core.getInstance().getBackend().getGroups())
            {
                System.out.println("\tName: " + g.getName());
                System.out.println("\t\tID: " + g.getId());
            }
    }
    
}
