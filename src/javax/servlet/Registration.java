/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javax.servlet;

import java.util.Map;
import java.util.Set;

/**
 *
 * @author unni_vm
 */
public interface Registration {
    
    String getClassName();
    
    String getInitParameter();
    
    Map<String, String> getInitParameters();
    
    String getName();
    
    boolean setInitParameter(String name, String value);
    
    Set<String> setInitParameters(Map<String,String> initParameters);
    
    static interface Dynamic{
    }
}
