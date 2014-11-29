/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utah.cs6964.management.access;

/**
 *
 * @author christopher
 */
public class Group {
    
    private int id;
    private String name;
    public Group(int id, String name)
    {
        this.id = id;
        this.name = name;
    }
    
    public int getId()
    {
        return this.id;
    }
    
    public String getName()
    {
        return this.name;
    }
}
