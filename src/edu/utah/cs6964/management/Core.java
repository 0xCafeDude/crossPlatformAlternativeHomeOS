/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utah.cs6964.management;

import edu.utah.cs6964.management.access.User;
import edu.utah.cs6964.management.backend.DataBackend;

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
    
    public boolean loginUser(String username, String password)
    {
        if(null == backend)
        {
            return false;
        }
        this.loggedInUser = backend.loginUser(username, password);
        return !(null == loggedInUser);
    }
    
    public void setBackend(DataBackend backend)
    {
        this.backend = backend;
    }
    
    public User getLoggedInUser()
    {
        return this.loggedInUser;
    }
    
}
