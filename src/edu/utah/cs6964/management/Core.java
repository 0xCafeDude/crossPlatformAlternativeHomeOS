/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utah.cs6964.management;

import edu.utah.cs6964.management.access.User;
import edu.utah.cs6964.management.backend.DataBackend;
import edu.utah.cs6964.management.backend.MySQLBackend;
import java.util.Properties;

/**
 *
 * @author christopher
 */
public class Core {
    
    private DataBackend backend;
    private User loggedInUser;
    
    private static Core instance = null;
    private Core()
    {
        this.backend = null;
    }
    
    public static Core getInstance()
    {
        if(null == instance)
        {
            instance = new Core();
        }
        
        return instance;
    }
    
    public boolean loadConfiguration(Properties configuration)
    {
        String dbName = configuration.getProperty("dbName", "");
        String dbHost = configuration.getProperty("dbHost", "");
        String dbUser = configuration.getProperty("dbUser", "");
        String dbPass = configuration.getProperty("dbPass", "");
            
        backend = new MySQLBackend(dbName, dbHost, dbUser, dbPass);
        
        if(null == backend)
        {
            return false;
        }
        
        return true;
    }
    
    public boolean loginUser(String username, String password)
    {
        if(null == backend)
        {
            return false;
        }
        this.loggedInUser = backend.loginUser(username, password);
        return !(null == loggedInUser);
    }
    
    
    public DataBackend getBackend()
    {
        return this.backend;
    }
    
    public User getLoggedInUser()
    {
        return this.loggedInUser;
    }
    
}
