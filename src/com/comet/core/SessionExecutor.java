/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comet.core;

import com.comet.servlet.impl.Session;
import com.comet.servlet.impl.SessionID;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Unni Vemanchery Mana
*/
public class SessionExecutor {
    
  long DELAY = 30;
    
  ScheduledThreadPoolExecutor stpe = new ScheduledThreadPoolExecutor(1); 
 
  private String sessionID ;
 
  Session session;
   
  private static boolean start = true;
  
  private static SessionExecutor se = new SessionExecutor();
  
   
  private SessionExecutor(){
  }
   
  public static void main(String []arg){
    SessionExecutor se = new SessionExecutor();
    se.start();
  }
    
  public void setTimeOut(int timeout){
     DELAY = timeout;
  }
    
  public synchronized void start(){
      if(!start) return;
      stpe.scheduleWithFixedDelay(new Runnable(){  
      int count = 0;   
      @Override
      public void run(){  
        sessionID = SessionID.getSessionID();
        session.onSessionCreated(sessionID);
      }  
      }, 0, DELAY, TimeUnit.MINUTES); 
      start = false;
  }
 
  public synchronized void start1(){
      sessionID = SessionID.getSessionID();
      session.onSessionCreated(sessionID);
  }
  
   public static SessionExecutor getInstance() {
    return se;
   }
   
   public void setSession(Session httpSession){
     session = httpSession;
   }
   
}
