/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utah.cs6964;

import edu.utah.cs6964.drivers.zipato.*;
import edu.utah.cs6964.management.access.User;
import edu.utah.cs6964.management.backend.DataBackend;
import edu.utah.cs6964.management.backend.MySQLBackend;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author christopher
 */
public class MainTest {
    
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
            
            /*Enumeration<String> propertyNames =
                    (Enumeration<String>) properties.propertyNames();
            
            while(propertyNames.hasMoreElements())
            {
                String propertyName = propertyNames.nextElement();
                System.out.println("\t\t" + propertyName + " has value " +
                        properties.getProperty(propertyName));
            }*/
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
                    Logger.getLogger(MainTest.class.getName()).log(Level.SEVERE, null, ex);
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
        
        ArrayList<User> users = backend.getUsers();
        
        Method defaultMethods[] = Object.class.getDeclaredMethods();
        
        ArrayList<Method> methods = new ArrayList<Method>(Arrays.asList(RGBBulb.class.getDeclaredMethods()));
        for(int i = 0; i < defaultMethods.length; ++i)
        {
            methods.remove(defaultMethods[i]);
        }
        for(int i = 0; i < methods.size(); ++i)
        {
            System.out.println(methods.get(i).getName());
        }
    }
}
