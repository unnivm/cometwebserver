/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comet.utils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Unni Vemanchery Mana
 */
public class FileUtil {
    
    static final int BUFF_SIZE = 10000;
    static final byte[] buffer = new byte[BUFF_SIZE];

    /*
     * This method is used to load static  resource such as .gif,.pdfs etc from 
     * an application folder on server.For example, if server is installed on 
     * c:\comet and 'test' is a sample web application, then path to resources 
     * under 'test' will be c:\comet\applications\test.Note here that 'applications'
     * will be appended automatically
    */
    public static byte[] loadResource(String path){
      byte [] b = null;
      InputStream is = null;
      try {
           is = new FileInputStream(new File(System.getProperty("user.dir")+ File.separator+ Constants.application+File.separator+path));
         }catch (FileNotFoundException ex){
           Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, ex);
           return null;
         }
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      int nRead;
      byte[] data = new byte[1024];
      try{
        while ((nRead = is.read(data, 0, data.length)) != -1){
            baos.write(data, 0, nRead);
        }
        baos.flush();
      }catch (IOException ex){
        Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, ex);
      }
      if( baos != null)
      b = baos.toByteArray();
        try {
            baos.close();
        } catch (IOException ex) {
            Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
      return b;
    }

    public static byte [] loadResource1( String path ) throws FileNotFoundException, IOException{
      InputStream in    = null;
      ByteArrayOutputStream out  = new ByteArrayOutputStream(); 
       byte [] b = null;
      try {
           in = new FileInputStream(new File(System.getProperty("user.dir")+ File.separator+ Constants.application+File.separator+path));
           while (true){
           synchronized (buffer){
            int amountRead = in.read(buffer);
            if(amountRead == -1){
               break;
            }
            out.write(buffer, 0, amountRead); 
           }
          }
         
          b = out.toByteArray();
     }finally{
      if (in != null){
         in.close();
      }
      if (out != null){
         out.close();
      }
     }
     return b; 
 }
    
    public static String readFile(String path){
     StringBuilder sb = new StringBuilder();
      try{
           FileInputStream fstream = new FileInputStream(new File(System.getProperty("user.dir")+ File.separator+ Constants.application+File.separator+path));
           DataInputStream in = new DataInputStream(fstream);
           BufferedReader br = new BufferedReader(new InputStreamReader(in));
           String strLine;
           while((strLine = br.readLine()) != null){
            sb.append(strLine).append("\r\n"); 
           }
     in.close();
    }catch (Exception e){//Catch exception if any
    }
     return sb.toString();
    }
    
   public static void readFiles(File file){ 
     if(file.isDirectory()){
       System.out.println("[DIR] "  + file.getName());
       if(Constants.application.equals(file.getParent())){
       }
    }else if(file.isFile()){
       System.out.println("[FILE] " + file.getName());
       if("web.xml".equals(file.getName())){
       }
    }    
    File[] children = file.listFiles();
     if(children != null){
        for(File child : children){ 
            readFiles(child); 
        } 
     }
   }
    
    public static void main(String []arg){
      double start = System.currentTimeMillis();
      //loadResource("unni/servlets/images/return.gif");
     
      readFiles(new File(System.getProperty("user.dir")+ File.separator+ Constants.application+File.separator));
       double end   = System.currentTimeMillis();
      System.out.println(" time taken " + (end - start));
      System.out.println("===== using loadResource==== ");
    }
    
    
}
