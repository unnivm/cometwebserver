/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comet.core;

import com.comet.servlet.impl.Session;
import java.io.Serializable;


/**
 *
 * @author unni_vm
 */
public class SessionContext implements Serializable {
    
 private boolean sessionCreated;  

 private Session session;
 
 private transient CometState ct;
 
 public SessionContext(){
 }

 public void setSessionCreated(boolean status){
   sessionCreated = status;
 }

 public boolean isSessionCreated(){
   return sessionCreated;		
 }

 public void setSession(Session session){
   this.session = session;
 }
  
 public Session getSession(){
   return session;
 }

 public void setContext(String context){
   session = new Session(this,ct,context);
 }
 
 public void setCometState(CometState ct){
   this.ct = ct;
 }
 
}
