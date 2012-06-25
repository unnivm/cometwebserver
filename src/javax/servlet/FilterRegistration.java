/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javax.servlet;

import java.util.Collection;

public interface FilterRegistration {

    void addMappingForServletNames(java.util.EnumSet<DispatcherType> dispatcherTypes, boolean isMatchAfter, java.lang.String... servletNames) ;

    void addMappingForUrlPatterns(java.util.EnumSet<DispatcherType> dispatcherTypes, boolean isMatchAfter, java.lang.String... urlPatterns);
    
    Collection<String> getServletNameMappings();
    
    Collection<String> getPatternMappings();
    
    public static interface Dynamic{
        
    }
}
