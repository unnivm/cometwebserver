/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comet.utils;

import com.comet.core.SessionContext;
import com.comet.servlet.impl.Session;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

/**
 *
 * @author unni_vm
 */
public class CometUtil{
    
    public static byte [] getCompressedSession(Session session){
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      DeflaterOutputStream dos   = new DeflaterOutputStream(baos);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(dos);
            oos.writeObject(session);
            oos.flush();
            oos.close();
        } catch (IOException ex) {
            Logger.getLogger(CometUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return baos.toByteArray();
    }
    
   public static byte [] getCompressedSession(SessionContext context){
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      DeflaterOutputStream dos   = new DeflaterOutputStream(baos);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(dos);
            oos.writeObject(context);
            oos.flush();
            oos.close();
        } catch (IOException ex){
            Logger.getLogger(CometUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return baos.toByteArray();
    }

    
    public static SessionContext getHttpSession(byte [] b){
      InflaterInputStream iis = new InflaterInputStream(new ByteArrayInputStream(b));
      ObjectInputStream ois = null;
      SessionContext s = null;
        try{
            ois = new ObjectInputStream(iis);
            s   = (SessionContext) ois.readObject();
        }catch (IOException ex){
          Logger.getLogger(CometUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
         catch (ClassNotFoundException ex){
           Logger.getLogger(CometUtil.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                ois.close();
            } catch (IOException ex) {
              Logger.getLogger(CometUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s; 
    }
 
    public static void getSerializedData(){
        FileInputStream fis;
        try {
            fis = new FileInputStream("session.ser");
        }catch (FileNotFoundException ex){
            Logger.getLogger(CometUtil.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        try {
            ObjectInputStream ois = new ObjectInputStream(fis);
            SessionContext sc = (SessionContext) ois.readObject();
            ois.close();
            fis.close();
            System.out.println(sc.getSession().getId());
        }catch (ClassNotFoundException ex){
            Logger.getLogger(CometUtil.class.getName()).log(Level.SEVERE, null, ex);
        }catch (IOException ex){
            Logger.getLogger(CometUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] arg){
        getSerializedData();
    }
}
