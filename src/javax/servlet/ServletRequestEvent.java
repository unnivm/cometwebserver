/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javax.servlet;

public class ServletRequestEvent extends java.util.EventObject{
    
   public ServletRequestEvent(ServletContext sc, ServletRequest req){
    super(sc);
   }
    
   ServletContext getServletContext(){
    return null;
   }
   
   ServletRequest getServletRequest(){
    return null;
   }
   
}
