/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utah.cs6964.management.interfaces.cli;

import edu.utah.cs6964.management.Core;
import edu.utah.cs6964.management.access.User;

/**
 *
 * @author christopher
 */
public class ViewUsersCommand implements Command {

    @Override
    public byte getMinimumAccessLevel() {
        return 4;
    }

    @Override
    public String getCommand() {
        return "users";
    }

    @Override
    public String getDescription() {
        return "View Users";
    }

    @Override
    public void doAction() {
        System.out.println("Users:");
        for(User u : Core.getInstance().getBackend().getUsers())
        {
            System.out.println("\tName: " + u.getFirstName() + " " + u.getLastName());
            System.out.println("\t\tID: " + u.getId());
            System.out.println("\t\tUsername: " + u.getUsername());
            System.out.println("\t\tAccess Level: " + u.getAccessLevel());
            System.out.print("\t\tGroup IDs: ");
            for(int i = 0; i < u.getGroups().size(); ++i)
            {
                System.out.print(u.getGroups().get(i));
                if(i < u.getGroups().size() - 1)
                {
                    System.out.print(" ");
                }
                else
                {
                    System.out.println();
                }
            }

        }
    }
    
}
