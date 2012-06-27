/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comet.core.event;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author unni_vm
 */
public class CometSessionEventDispatcher {
    
    private List<HttpSessionListener> list         =  new ArrayList<HttpSessionListener>();
    
    private static CometSessionEventDispatcher csed = new CometSessionEventDispatcher();
    
    public void addListener(HttpSessionListener el){
      list.add(el);
    }
    
    public void removeListenr(HttpSessionListener el){
      list.remove(el);
    }
    
    public void notifySessionEvent(HttpSessionEvent event){
        for(HttpSessionListener el : list){
         el.sessionCreated(event);
         el.sessionDestroyed(event);
        }
    }
    
    public static CometSessionEventDispatcher getInstance(){
     return csed;
    }
    
}
