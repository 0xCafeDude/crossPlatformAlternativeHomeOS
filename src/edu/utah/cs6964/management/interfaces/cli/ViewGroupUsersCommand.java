/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utah.cs6964.management.interfaces.cli;

import edu.utah.cs6964.management.Core;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author christopher
 */
public class ViewGroupUsersCommand implements Command {

    @Override
    public byte getMinimumAccessLevel() {
        return 4;
    }

    @Override
    public String getCommand() {
        return "group_users";
    }

    @Override
    public String getDescription() {
        return "View Group Users for a specific group";
    }

    @Override
    public void doAction() {
        System.out.print("Group ID? ");
        Scanner input = new Scanner(System.in);
        try {
            int id = input.nextInt();
            ArrayList<Integer> userIDs = Core.getInstance().getBackend().getUsersForGroup(id);
            for(int i = 0; i < userIDs.size(); ++i)
            {
                System.out.println("\t\t" + userIDs.get(i));
            }
        } catch(Exception ex)
        {
            // Intentionally Blank
        }
    }
    
}
