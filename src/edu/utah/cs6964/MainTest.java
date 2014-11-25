/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utah.cs6964;

import edu.utah.cs6964.drivers.zipato.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author christopher
 */
public class MainTest {
    
    public static void main(String [] args) {
        
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
