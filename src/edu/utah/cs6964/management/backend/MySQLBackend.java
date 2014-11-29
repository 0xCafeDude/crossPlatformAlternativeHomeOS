/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utah.cs6964.management.backend;

import edu.utah.cs6964.management.access.Group;
import edu.utah.cs6964.management.access.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author christopher
 */
public class MySQLBackend implements DataBackend {
    
    private Connection con = null;

    public MySQLBackend(String dbName, String dbHost, String dbUser,
                        String dbPassword)
    {
       try
       {
           con = DriverManager.getConnection("jdbc:mysql://" + dbHost + "/" + dbName, dbUser, dbPassword);
       }
       catch(SQLException ex)
       {
           System.out.println(ex.getMessage());
       }
    }
    
    protected void finalize()
    {
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(MySQLBackend.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public ArrayList<User> getUsers() {
        ArrayList<User> returnValue = new ArrayList<User>();
        try {
            Statement query = con.createStatement();
            ResultSet results = query.executeQuery("Select `ID` from `Users`");
            while(results.next())
            {
                User tempUser = getUser(results.getInt(1));
                if(null != tempUser)
                {
                    returnValue.add(tempUser);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MySQLBackend.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returnValue;
    }

    @Override
    public User getUser(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User getUser(int id) {
        try {
            Statement query = con.createStatement();
            ResultSet results = query.executeQuery("Select * from `Users` WHERE `ID` = " + id + " LIMIT 1");
            if(results.next())
            {
                Statement query2 = con.createStatement();
                ResultSet results2 = query.executeQuery("Select `GroupID` from `XREF_Users_Groups` where `UserID` = " + id);
                ArrayList<Group> groups = new ArrayList<Group>();
                while(results2.next())
                {
                    Group tempGroup = getGroup(results2.getInt(1));
                    if(tempGroup != null)
                    {
                        groups.add(tempGroup);
                    }
                }
                return new User(results.getInt("ID"), results.getString("Username"),
                                results.getString("FirstName"), results.getString("LastName"),
                                results.getByte("AccessLevel"), groups);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MySQLBackend.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ArrayList<Group> getGroups() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Group getGroup(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Group getGroup(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    protected ArrayList<Group> getGroupsForUser(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<User> getUsersForGroup(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Byte, String> getAccessLevels() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
