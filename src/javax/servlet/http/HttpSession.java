/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javax.servlet.http;

import java.util.Enumeration;
import javax.servlet.ServletContext;

 public interface HttpSession {

  public String getId();
	 
  public void setAttribute(String name, String value);
	 
  public Object getAttribute(String name);
         
  public Enumeration getAttributeNames();
         
  public long getCreationTime();
         
  public long getLastAccessedTime();
         
  public int getMaxInactiveInterval();
         
  public ServletContext getServletContext() ;
	 
 }

