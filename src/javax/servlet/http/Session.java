/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javax.servlet.http;

import java.util.*;
import javax.servlet.ServletContext;

 public class Session implements HttpSession {

   public HashMap mapsession = new HashMap();

   private  String sessionid = null;
	
   public Map sessionmap = new HashMap();

   public   ArrayList<String> sessionlist = new ArrayList<String>();
	
   private long creationTime;
   
   private ServletContext context ;
   
   public Session(){
      if(sessionid == null){
	//sessionid = UUID.randomUUID().toString();
        sessionid = SessionID.getSessionID();
        Date now = new Date();
        creationTime = now.getTime();
	sessionlist.add(sessionid);
        System.out.println("sessionlist in session..... "+sessionlist);
	}
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
   public void setAttribute(String name, String value){
	if(mapsession.containsKey(sessionid)) {
	    sessionmap = (Map) mapsession.get(sessionid);
	} else {
	    sessionmap.put(name, value);
	    mapsession.put(sessionid, sessionmap);
	}
   }
	
  public ArrayList getSessionList(){
    return sessionlist;
  }

  @Override
  public Enumeration getAttributeNames(){
    return new Vector(sessionmap.keySet()).elements();
  }

  @Override
  public long getCreationTime(){
    return creationTime;
  }

  @Override
  public long getLastAccessedTime() {
    return 0;
  }

  @Override
  public int getMaxInactiveInterval() {
    return 0;
  }

  @Override
  public ServletContext getServletContext() {
    return context;
  }
	
 }
