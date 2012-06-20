/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comet.core;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

public class CustomClassLoader extends ClassLoader {
    
    private Hashtable classes = new Hashtable();
    
    public CustomClassLoader(){
      super(CustomClassLoader.class.getClassLoader());
    }
  
    @Override
    public Class loadClass(String className) throws ClassNotFoundException {
      return findClass(className);
    }
 
    @Override
    public Class findClass(String className){
        byte classByte[];
        Class result=null;
        result = (Class)classes.get(className);
        if(result != null){
          return result;
        }
        try{
            return findSystemClass(className);
        }catch(Exception e){
         e.printStackTrace();
        }
        try{
           String classPath =    ((String)ClassLoader.getSystemResource(className.replace('.',File.separatorChar)+".class").getFile()).substring(1);
           classByte = loadClassData(classPath);
            result = defineClass(className,classByte,0,classByte.length,null);
            classes.put(className,result);
            return result;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        } 
    }
 
    private byte[] loadClassData(String className) throws IOException {
        File f ;
        f = new File(className);
        int size = (int)f.length();
        byte buff[] = new byte[size];
        FileInputStream fis = new FileInputStream(f);
        DataInputStream dis = new DataInputStream(fis);
        dis.readFully(buff);
        dis.close();
        return buff;
    }
       
    public static void main(String [] args) throws Exception{
        CustomClassLoader test = new CustomClassLoader();
        long start  = System.currentTimeMillis();
        Class cl = test.loadClass("com.comet.test.Test");
        long end = System.currentTimeMillis();
        System.out.println(" time taken to load class " +  (end - start));
        System.out.println(" cl " + cl.toString());
        test.loadClass("com.comet.test.Test");
     }
       
}
 
 

 

 
  

