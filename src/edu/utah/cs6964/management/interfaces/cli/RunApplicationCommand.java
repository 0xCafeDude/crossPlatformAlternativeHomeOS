/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utah.cs6964.management.interfaces.cli;

import edu.utah.cs6964.applications.ApplicationManager;
import edu.utah.cs6964.management.Core;
import edu.utah.cs6964.modules.Module;
import edu.utah.cs6964.roles.applications.Application;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author christopher
 */
public class RunApplicationCommand implements Command{

    @Override
    public byte getMinimumAccessLevel() {
        return 0;
    }

    @Override
    public String getCommand() {
        return "run";
    }

    @Override
    public String getDescription() {
        return "Runs a specified application";
    }

    @Override
    public void doAction() {
        ApplicationManager a = ApplicationManager.getInstance();
        List<Application> apps = a.getAllApplications();
        System.out.print("Application? ");
        Scanner input = new Scanner(System.in);
        try {
            int index = input.nextInt();
            if((index - 1) < apps.size())
            {
                Module temp = (Module) apps.get((index -1));
                temp.start();
            }
        } catch(Exception ex)
        {
        	ex.printStackTrace();
        }
    }
    
}
