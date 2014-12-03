/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utah.cs6964.management;

import edu.utah.cs6964.management.access.User;
import edu.utah.cs6964.management.backend.DataBackend;
import edu.utah.cs6964.management.backend.MySQLBackend;
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
        
        Console in = System.console();
        User user = null;
        boolean firstTime = true;
        while(null == user)
        {
            if(!firstTime)
            {
                System.out.println("Login Failed");
            }
            System.out.println("Enter Username:");
            String username = in.readLine();
            System.out.println("Enter Password:");
            String password = new String(in.readPassword());

            user = backend.loginUser(username, password);
            firstTime = false;
        }
            
    }
}
