/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utah.cs6964.management.interfaces;

import edu.utah.cs6964.management.Core;
import edu.utah.cs6964.management.access.User;
import edu.utah.cs6964.management.backend.DataBackend;
import edu.utah.cs6964.management.backend.MySQLBackend;
import edu.utah.cs6964.management.interfaces.cli.*;
import java.io.Console;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author christopher
 */
public class CLI {
    private static final String DEFAULT_PROPERTIES_FILE_NAME = "config.properties";
    private static String propertiesFileName = DEFAULT_PROPERTIES_FILE_NAME;
    
    public static void main(String [] args) {
        
        boolean debug = false;
        
        for(int i = 0; i < args.length; ++i)
        {
            if(args[i].equalsIgnoreCase("--config") && i < args.length - 1)
            {
                propertiesFileName = args[i + 1];
            }
            else if(args[i].equalsIgnoreCase("--debug"))
            {
                debug = true;
            }
        }
        
        if(debug)
        {
            System.out.println("Configuration:");
            System.out.println("\tProperties File: " + propertiesFileName);
        }
        
        // Try to read the configuration file
        Properties properties = new Properties();
        InputStream input = null;
        
        try
        {
            input = new FileInputStream(propertiesFileName);
            
            properties.load(input);
        }
        catch(FileNotFoundException ex)
        {
            System.out.println("Config file not found");
        }
        catch(IOException ex)
        {
            System.out.println("General IO Exception occurred: " + ex.getMessage());
        }
        finally
        {
            if(null != input)
            {
                try {
                    input.close();
                } catch (IOException ex) {
                    Logger.getLogger(CLI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        DataBackend backend = null;
        
        // Figure out the backend
        String backendValue =  properties.getProperty("backend", "");
        if(backendValue.equalsIgnoreCase("mysql"))
        {
            String dbName = properties.getProperty("dbName", "");
            String dbHost = properties.getProperty("dbHost", "");
            String dbUser = properties.getProperty("dbUser", "");
            String dbPass = properties.getProperty("dbPass", "");
            
            if(debug)
            {
                System.out.println("Using MySQL Backend with the following values:");
                System.out.println("\tdbName: " + dbName);
                System.out.println("\tdbHost: " + dbHost);
                System.out.println("\tdbUser: " + dbUser);
                System.out.println("\tdbPass: " + dbPass);
            }
            
            backend = new MySQLBackend(dbName, dbHost, dbUser, dbPass);
        }
        
        if(null == backend)
        {
            throw new Error("No valid backend found");
        }
        
        Core c = Core.getInstance();
        c.setBackend(backend);
        
        Console in = System.console();
        
        boolean firstTime = true;
        String username, password;
        do
        {
            if(!firstTime)
            {
                System.out.println("Login Failed");
            }
            System.out.println("Enter Username:");
            username = in.readLine();
            System.out.println("Enter Password:");
            password = new String(in.readPassword());

            firstTime = false;
        } while(!c.loginUser(username, password));
        
        System.out.println("Welcome, " + c.getLoggedInUser().getFirstName() +
                           " " + c.getLoggedInUser().getLastName());
        System.out.println("Type 'quit' to exit or 'list' to list commands");
        
        Command[] commands = {new ViewUsersCommand(),
                              new ViewGroupsCommand(),
                              new ViewAccessLevelsCommand(),
                              new ViewGroupUsersCommand()};
        String command = "";
        Scanner scan = new Scanner(System.in);
        while(true)
        {
            System.out.print("Command? ");
            command = scan.nextLine();
            if(command.equalsIgnoreCase("quit"))
            {
                // Just leave
                break;
            }
            else if(command.equalsIgnoreCase("list"))
            {
                System.out.println("Commands:");
                System.out.println("\tlist: List commands");
                for(int i = 0; i < commands.length; ++i)
                {
                    if(commands[i].getMinimumAccessLevel() <= c.getLoggedInUser().getAccessLevel())
                    {
                        System.out.println("\t" + commands[i].getCommand() + ": " + commands[i].getDescription());
                    }
                }
                System.out.println("\tquit: Quit the application");
            }
            else
            {
                for(int i = 0; i < commands.length; ++i)
                {
                    if(command.equalsIgnoreCase(commands[i].getCommand()) &&
                       commands[i].getMinimumAccessLevel() <= c.getLoggedInUser().getAccessLevel())
                    {
                        commands[i].doAction();
                    }
                }
            }
        }
    }
}
