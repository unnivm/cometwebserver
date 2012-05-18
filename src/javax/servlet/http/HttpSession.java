/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javax.servlet.http;

 public interface HttpSession {

	 public String getId();
	 
	 public void setAttribute(String name, String value);
	 
	 public Object getAttribute(String name);
	 
 }

