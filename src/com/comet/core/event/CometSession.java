/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comet.core.event;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author unni_vm
 */
public class CometSession implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
      
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        
    }
    
}
