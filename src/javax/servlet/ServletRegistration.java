/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javax.servlet;

import java.util.Collection;
import java.util.Set;

/**
 *
 * @author unni_vm
 */
public interface ServletRegistration extends Registration {
    
    Set<String> addMapping(String... urlPatterns);
    
    Collection<String> getMappings();

    String getRunAsRole();
    
    static interface Dynamic{
        
    }
}
