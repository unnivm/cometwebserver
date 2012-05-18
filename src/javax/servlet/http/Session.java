/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javax.servlet.http;

 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.Map;
 import java.util.UUID;

 public class Session implements HttpSession {

	public HashMap mapsession = new HashMap();

	private  String sessionid = null;
	
	public Map sessionmap = new HashMap();

	public  static ArrayList<String> sessionlist = new ArrayList<String>();
	
	public Session(){
		System.out.println("sessionid "+sessionid);
		if(sessionid == null){
		   sessionid = UUID.randomUUID().toString();
		   sessionlist.add(sessionid);
		   System.out.println("sessionlist in session..... "+sessionlist);
		}
	}
	
	public String getId(){
		return sessionid;
	}

	public Object getAttribute(String name) {
		Object object = sessionmap.get(name);
		return object;
	}

	public void setAttribute(String name, String value) {
		if(mapsession.containsKey(sessionid)) {
			sessionmap = (Map) mapsession.get(sessionid);
		} else {
			sessionmap.put(name, value);
			mapsession.put(sessionid, sessionmap);
		}
	}
	
	public static ArrayList getSessionList(){
		return sessionlist;
	}
	
 }

