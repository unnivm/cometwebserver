/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javax.servlet.http;

/**
 *
 * @author unni_vm
 */
public interface HttpSessionListener {
    void sessionCreated(HttpSessionEvent se);
    void sessionDestroyed(HttpSessionEvent se);
}
