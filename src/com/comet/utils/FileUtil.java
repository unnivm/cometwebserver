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
      ByteArrayOutputStream buffer = new ByteArrayOutputStream();
      int nRead;
      byte[] data = new byte[1024];
      try{
        while ((nRead = is.read(data, 0, data.length)) != -1){
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
      }catch (IOException ex){
        Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, ex);
      }
      if( buffer != null)
      b = buffer.toByteArray();
        try {
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public static void main(String []arg){
        //System.out.println(FileUtil.readFile("unni/index.html"));
        Map m = new HashMap();
        m.put("firstname=eed&lastname=ccccqa","dddd");
        System.out.println(m.get("firstname=eed&lastname=ccccqa"));
        StringBuilder sb = new StringBuilder();
        sb.append("<form action=\"").append("RequestParamExample\" ");
        System.out.println(" sb "+sb.toString());
    }
}
