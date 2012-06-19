/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comet.core;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.Session;
import javax.servlet.http.SessionID;

/**
 *
 * @author Unni Vemanchery Mana
 */
public class SessionExecutor {
    
  long DELAY = 1;
    
  ScheduledThreadPoolExecutor stpe = new ScheduledThreadPoolExecutor(1); 
 
   private String sessionID ;
 
   Session session;
   
   public SessionExecutor(Session session){
     this.session = session;
   }
   
   public SessionExecutor(){
   }
   
   public static void main(String []arg){
     SessionExecutor se = new SessionExecutor();
     se.start();
   }
    
   public void setTimeOut(int timeout){
     DELAY = timeout;
   }
    
   public void start(){
      stpe.scheduleWithFixedDelay(new Runnable(){  
      int count = 0;   
      @Override
      public void run(){  
        sessionID = SessionID.getSessionID();
        session.onSessionCreated(sessionID);
      }  
      }, 0, DELAY, TimeUnit.SECONDS); 
   }
    
}
