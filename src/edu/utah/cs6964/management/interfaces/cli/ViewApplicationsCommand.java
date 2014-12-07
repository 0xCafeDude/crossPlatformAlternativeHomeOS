/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utah.cs6964.management.interfaces.cli;

import edu.utah.cs6964.applications.ApplicationManager;
import edu.utah.cs6964.roles.applications.Application;
import java.util.List;

/**
 *
 * @author christopher
 */
public class ViewApplicationsCommand implements Command {

    @Override
    public byte getMinimumAccessLevel() {
        return 0;
    }

    @Override
    public String getCommand() {
        return "applications";
    }

    @Override
    public String getDescription() {
        return "View Applications";
    }

    @Override
    public void doAction() {
        System.out.println("Applications:");
        ApplicationManager a = ApplicationManager.getInstance();
        List<Application> apps = a.getAllApplications();
        for(int i = 0; i < apps.size(); ++i)
        {
            System.out.println((i+1) + apps.get(i).getUserName());
        }
    }
    
}
