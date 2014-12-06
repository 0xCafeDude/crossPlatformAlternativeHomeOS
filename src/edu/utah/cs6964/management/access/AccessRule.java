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
public class AccessRule {
    private int id;
    private String fromModule;
    private String toModule;
    private int groupID;
    private ArrayList<DayOfWeek> days;
    private Time start;
    private Time end;
    
    public AccessRule(int id, String fromModule, String toModule, int groupID, ArrayList<DayOfWeek> days, Time start, Time end)
    {
        this.id = id;
        this.fromModule = fromModule;
        this.toModule = toModule;
        this.groupID = groupID;
        this.days = days;
        this.start = start;
        this.end = end;
    }
    
    public int getId() {
        return this.id;
    }

    public String getFromModule() {
        return this.fromModule;
    }

    public String getToModule() {
        return this.toModule;
    }

    public int getGroupID() {
        return this.groupID;
    }

    public ArrayList<DayOfWeek> getDays() {
        return this.days;
    }

    public Time getStart() {
        return this.start;
    }

    public Time getEnd() {
        return this.end;
    }
}
