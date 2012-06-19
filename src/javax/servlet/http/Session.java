/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javax.servlet.http;

import com.comet.core.SessionExecutor;
import com.comet.utils.Constants;
import java.util.*;
import javax.servlet.ServletContext;

 public class Session implements HttpSession {

   public  HashMap mapsession = new HashMap();

   private String sessionid = null;
	
   public  Map sessionmap     = new HashMap();

   public ArrayList<String> sessionlist = new ArrayList<String>();
	
   private long creationTime;
   
   private ServletContext context;
   
   Cookie cookie;
   
   SessionExecutor se;
   
   public Session(){
     createSessionID();
   }

   private void createSessionID(){
      se = new SessionExecutor(this);
      se.start();
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
      if(mapsession.containsKey(sessionid)){
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
  public long getLastAccessedTime(){
    return 0;
  }

  @Override
  public int getMaxInactiveInterval(){
    return 0;
  }

  @Override
  public ServletContext getServletContext(){
    return context;
  }

   public boolean isAValidSession(String id){
     return sessionlist.contains(id);
   }
   
   public void onSessionCreated(String id){
     Date now  = new Date();
     creationTime = now.getTime();
     sessionid = id;
     sessionlist.add(sessionid);
     cookie = new Cookie(Constants.JSESSIONID, sessionid);
   }
   
   public void setSessionID(String id){
       sessionid = id;
   }
 }
