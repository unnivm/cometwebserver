/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comet.container;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author unni_vm
 */
public class ContainerContext {
    
    private Map<String, Object> projectMap;
    
    protected void setProjectMap(Map projectMap){
        this.projectMap = projectMap;
    }
    
    public Map getProjectMap(){
        return projectMap;
    }
}
