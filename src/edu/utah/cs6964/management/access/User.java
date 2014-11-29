/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utah.cs6964.management.access;

import java.util.ArrayList;

/**
 *
 * @author christopher
 */
public class User {
    
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private byte accessLevel;
    private ArrayList<Integer> groups;
    
    public User(int id, String username, String firstName, String lastName, byte accessLevel, ArrayList<Integer> groups)
    {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accessLevel = accessLevel;
        this.groups = groups;
    }

    public int getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public byte getAccessLevel() {
        return this.accessLevel;
    }

    public ArrayList<Integer> getGroups() {
        return this.groups;
    }
    
    
}
