/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javax.servlet;

/**
 *
 * @author unni_vm
 */
public interface ServletRequestListener {
    
    void requestDestroyed(ServletRequestEvent sre);
    
    void requestInitialized(ServletRequestEvent sre); 
}
