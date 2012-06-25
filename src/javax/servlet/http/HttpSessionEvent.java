/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javax.servlet.http;

import java.util.EventObject;

public class HttpSessionEvent extends EventObject {
    
    public HttpSessionEvent(HttpSession source){
     super(source);
    }
    
    public HttpSession getSession(){
     return (HttpSession) super.getSource();
    }
    
}
