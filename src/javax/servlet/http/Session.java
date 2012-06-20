/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javax.servlet.http;

import com.comet.core.SessionExecutor;
import com.comet.core.event.CometSession;
import com.comet.core.event.CometSessionEventDispatcher;
import com.comet.utils.Constants;
import java.util.*;
import javax.servlet.ServletContext;

 public class Session implements HttpSession{

   public  HashMap mapsession = new HashMap();

   private String sessionid   = null;
	
   public  Map sessionmap     = new HashMap();

   public ArrayList<String> sessionlist = new ArrayList<String>();
	
   private long creationTime;
   
   private ServletContext context;
   
   Cookie cookie;
   
   SessionExecutor se;
   
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
      if(start){ 
       se = new SessionExecutor(this);
       se.start();
       start = false;
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
    return sessionAccess.get(sessionid);
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
     sessionlist.add(sessionid);
     sessionAccess.put(id, creationTime);
     cookie = new Cookie(Constants.JSESSIONID, sessionid);
   }
   
   public void setSessionID(String id){
     sessionid = id;
   }
   
   /*
    * 
    */
   public void invalidate() throws IllegalStateException {
       
   }
   
 }
