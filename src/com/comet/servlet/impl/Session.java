/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comet.servlet.impl;

import com.comet.core.SessionExecutor;
import com.comet.core.event.CometSession;
import com.comet.utils.Constants;
import java.io.*;
import java.util.*;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

 public class Session implements HttpSession,Serializable{

   public   HashMap mapsession = new HashMap();

   private  String sessionid   = null;
	
   public   Map sessionmap     = new HashMap();

   public ArrayList<String> sessionlist = new ArrayList<String>();
	
   private  long creationTime;
   
   private  ServletContext context;
   
   transient Cookie cookie;
   
   transient SessionExecutor se;
   
   private boolean start = true;
   
   private int defaultTime = 30;
   
   /*
    * collection for storing sessionID and time
   */
   private HashMap<String, Long> sessionAccess = new HashMap<String, Long>();
   
   private CometSession cometSession;
   
   public Session(){
    createSessionID();
   }
   
   private void createSessionID(){
     se = SessionExecutor.getInstance();
     se.setSession(this);
     se.start1();
   }
   
   @Override
   public String getId(){
     return sessionid;
   }

   @Override
   public Object getAttribute(String name){
    Object object = sessionmap.get(name);
    return object;
   }

   @Override
   public void setAttribute(String name, Object value){
       System.out.println("[ inside setAttribute() method ]");
      if(mapsession.containsKey(sessionid)){
         System.out.println("---- 00000----") ;
	 sessionmap = (Map) mapsession.get(sessionid);
         sessionmap.put(name, value);
      } else {
	 sessionmap.put(name, value);
	 mapsession.put(sessionid, sessionmap);
         System.out.println("-- 10001----") ;
      }
   }
	
  public ArrayList getSessionList(){
    return sessionlist;
  }

  @Override
  public Enumeration getAttributeNames(){
    System.out.println(" what is session map " + sessionmap);  
    return new Vector(sessionmap.keySet()).elements();
  }

  @Override
  public long getCreationTime(){
    return creationTime;
  }

  @Override
  public long getLastAccessedTime(){
    return sessionAccess.get(sessionid);
    //return 0;
  }

  @Override
  public int getMaxInactiveInterval(){
    return defaultTime;
  }

  @Override
  public ServletContext getServletContext(){
    return context;
  }

  public boolean isAValidSession(String id){
    return sessionlist.contains(id);
  }
   
   public void onSessionCreated(String id){
     Date now     = new Date();
     creationTime = now.getTime();
     sessionid    = id;
     System.out.println("session id :: " + sessionid);
     sessionlist.add(sessionid);
     sessionAccess.put(id, creationTime);
     cookie = new Cookie(Constants.JSESSIONID, sessionid);
   }
   
   public void setSessionID(String id){
     sessionid = id;
   }
   
   public Cookie getCookie(){
     return cookie;    
   }
   
   /*
    * 
    */
   public void invalidate() throws IllegalStateException {
       
   }
   
   public static void main(String []arg ) throws FileNotFoundException, IOException, ClassNotFoundException{
       Session s = new Session();
       s.setSessionID("34234ssdfsdfsdf453453457867978");
      // FileOutputStream fos   = new FileOutputStream("db");
       ByteArrayOutputStream baos = new ByteArrayOutputStream();
       GZIPOutputStream gz    = new GZIPOutputStream(baos);
       ObjectOutputStream oos = new ObjectOutputStream(gz);
       oos.writeObject(s);
       oos.flush();
       oos.close();
      // fos.close();
       System.out.println("==== reading ======");
      
      long st = System.currentTimeMillis();
    //  FileInputStream fis = new FileInputStream("db");
      GZIPInputStream gs = new GZIPInputStream(new ByteArrayInputStream(baos.toByteArray()));
      ObjectInputStream ois = new ObjectInputStream(gs);
      s = (Session) ois.readObject();
      ois.close();
    //  fis.close();
      long end = System.currentTimeMillis();
      System.out.println(" === diff ===" + (end-st));
      if(s != null) System.out.println("s  is retrieved....."+s.sessionid+" byte array size " + baos.toByteArray().length);
      
      System.out.println("====== Using deflator strem ");
      s = new Session();
      ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
      DeflaterOutputStream dos    = new DeflaterOutputStream(baos1);
      oos = new ObjectOutputStream(dos);
      oos.writeObject(s);
      oos.flush();
      oos.close();
      
      System.out.println(" reading again=== " );
      
      st = System.currentTimeMillis();
      InflaterInputStream dis = new InflaterInputStream(new ByteArrayInputStream(baos1.toByteArray()));
      ois = new ObjectInputStream(dis);
      s   = (Session) ois.readObject();
      ois.close();
      end = System.currentTimeMillis();
      System.out.println(" === diff ===" + (end-st));
      if(s != null) System.out.println("s  is retrieved....."+s.sessionid+" byte array size " + baos.toByteArray().length);
   }
   
 }
