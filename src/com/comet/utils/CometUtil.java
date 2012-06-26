/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comet.utils;

import com.comet.core.CometState;
import com.comet.core.SessionContext;
import com.comet.servlet.impl.Session;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.Map;
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
      try{
          ObjectOutputStream oos = new ObjectOutputStream(dos);
          oos.writeObject(context);
          oos.flush();
          oos.close();
        }catch (IOException ex){
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
      }catch (ClassNotFoundException ex){
           Logger.getLogger(CometUtil.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                if(ois != null)
                   ois.close();
                if(iis != null)
                   iis.close(); 
            }catch (IOException ex){
              Logger.getLogger(CometUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s; 
    }
 
    public static void getSerializedData(){
        FileInputStream fis;
        try {
            fis = new FileInputStream("session1.ser");
        }catch (FileNotFoundException ex){
            Logger.getLogger(CometUtil.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        try {
            ObjectInputStream ois = new ObjectInputStream(fis);
            SessionContext sc     = (SessionContext) ois.readObject();
            ois.close();
            fis.close();
            System.out.println(sc.getSession().getId());
            System.out.println(sc.getSession().sessionmap);
            System.out.println(sc.getSession().mapsession);
            System.out.println(sc.getSession().getLastAccessedTime());
            System.out.println(sc.getSession().getSessionList());
        }catch (ClassNotFoundException ex){
            Logger.getLogger(CometUtil.class.getName()).log(Level.SEVERE, null, ex);
        }catch (IOException ex){
            Logger.getLogger(CometUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void serializeData(){
        SessionContext sc = new SessionContext();
        sc.setContext("unni");
        Session session = sc.getSession();
        if(session == null) System.out.println(" .... session is null ");
        else System.out.println(" ... session IS NOT null.....");
                session.setAttribute("car", "Suzuki");
        CometState cs = CometState.getCometState();
        cs.store("unni", ByteBuffer.wrap(getCompressedSession(sc))); //storing context ref into map
         Map<String, ByteBuffer> map = cs.getCometState().getValue();
         System.out.println(" map " + map);
         Iterator iterator = map.keySet().iterator();
        try{
          FileOutputStream fos  = new FileOutputStream("session1.ser");
          ObjectOutputStream oos = new ObjectOutputStream(fos);
          while(iterator.hasNext()){
              String key = iterator.next().toString();
              ByteBuffer buf = map.get(key);
              oos.writeObject(CometUtil.getHttpSession(buf.array()));
              oos.flush();
          }
          oos.close();
          fos.close();
        }catch(Exception e){
         e.printStackTrace();
        }
        
    }
    
    private static void deserializeData(){
    }
    
    public static void main(String[] arg){
      //serializeData();
        long st = System.currentTimeMillis();
        getSerializedData();
        long end = System.currentTimeMillis();
        long tot = end - st;
        System.out.println(" tot " + tot);
    }
}
