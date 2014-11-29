/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utah.cs6964.management.backend;

import edu.utah.cs6964.management.access.Group;
import edu.utah.cs6964.management.access.User;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author christopher
 */
public interface DataBackend {
    public ArrayList<User> getUsers();
    public User getUser(String name);
    public User getUser(int id);
    public ArrayList<Group> getGroups();
    public Group getGroup(String name);
    public Group getGroup(int id);
    public ArrayList<User> getUsersForGroup(int id);
    public Map<Byte, String> getAccessLevels();
}
