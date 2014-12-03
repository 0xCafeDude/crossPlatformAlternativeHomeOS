/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utah.cs6964.management.backend;

import edu.utah.cs6964.management.access.Group;
import edu.utah.cs6964.management.access.User;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
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
    public User getUser(String username) {
        try {
            Statement query = con.createStatement();
            ResultSet results = query.executeQuery("Select `ID` from `Users` WHERE `Username` = '" + username + "' LIMIT 1");
            if(results.next())
            {
                return getUser(results.getInt(1));
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(MySQLBackend.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public User getUser(int id) {
        try {
            Statement query = con.createStatement();
            ResultSet results = query.executeQuery("Select * from `Users` WHERE `ID` = " + id + " LIMIT 1");
            if(results.next())
            {
                Statement query2 = con.createStatement();
                ResultSet results2 = query2.executeQuery("Select `GroupID` from `XREF_Users_Groups` where `UserID` = " + id);
                ArrayList<Integer> groups = new ArrayList<Integer>();
                while(results2.next())
                {
                    groups.add(results2.getInt(1));
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
        ArrayList<Group> returnValue = new ArrayList<Group>();
        try {
            Statement query = con.createStatement();
            ResultSet results = query.executeQuery("Select `ID` from `Groups`");
            while(results.next())
            {
                Group tempGroup = getGroup(results.getInt(1));
                if(null != tempGroup)
                {
                    returnValue.add(tempGroup);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MySQLBackend.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returnValue;
    }

    @Override
    public Group getGroup(String name) {
        try {
            Statement query = con.createStatement();
            ResultSet results = query.executeQuery("Select `ID` from `Groupss` WHERE `name` = '" + name + "' LIMIT 1");
            if(results.next())
            {
                return getGroup(results.getInt(1));
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(MySQLBackend.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Group getGroup(int id) {
        try {
            Statement query = con.createStatement();
            ResultSet results = query.executeQuery("Select * from `Groups` WHERE `ID` = " + id + " LIMIT 1");
            if(results.next())
            {
                return new Group(results.getInt("ID"), results.getString("Name"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MySQLBackend.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ArrayList<Integer> getUsersForGroup(int id) {
        ArrayList<Integer> returnValue = new ArrayList<Integer>();
        try {
            Statement query = con.createStatement();
            ResultSet results = query.executeQuery("Select `UserID` from `XREF_Users_Groups` where `GroupID` = " + id);
            while(results.next())
            {
                returnValue.add(results.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MySQLBackend.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returnValue;
    }

    @Override
    public Map<Byte, String> getAccessLevels() {
        Map<Byte, String> returnValue = new HashMap<Byte, String>();
        try {
            Statement query = con.createStatement();
            ResultSet results = query.executeQuery("Select * from `AccessLevels` ORDER BY `Level` ASC");
            while(results.next())
            {
                returnValue.put(results.getByte("Level"), results.getString("Name"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MySQLBackend.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returnValue;
    }

    @Override
    public User loginUser(String username, String password) {
        try {
            Statement query = con.createStatement();
            ResultSet results = query.executeQuery("Select `ID` from `Users` WHERE sha1(`username`) = '" + SHA1(username) + "' AND  `password` = '" + SHA1(password) + "' LIMIT 1");
            if(results.next())
            {
                return getUser(results.getInt(1));
            }
            return null;
        }
        catch (SQLException ex) {
            Logger.getLogger(MySQLBackend.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(MySQLBackend.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private String SHA1(String input) throws NoSuchAlgorithmException
    {
        MessageDigest sha1 = MessageDigest.getInstance("SHA1");
        byte[] result = sha1.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < result.length; ++i)
        {
            sb.append(Integer.toString((result[i] & 0xFF) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
    
}
