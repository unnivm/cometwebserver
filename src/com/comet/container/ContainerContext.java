/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comet.container;

import java.util.Map;

/**
 *
 * @author unni_vm
 */
public class ContainerContext {
    
    private Map<String, Object> projectMap;
    
    private CometContainer container;
    
    private static ContainerContext context;
    
    private ContainerContext(){
        
    }
    
    protected void setProjectMap(Map projectMap){
        this.projectMap = projectMap;
    }
    
    public Map getProjectMap(){
        return projectMap;
    }
    
    public CometContainer getContainer(){
        return container;
    }
    
    
    public static ContainerContext getContainerContext(){
        if(context == null){
            context = new ContainerContext();
        }
        return context;
    }
    
    public void setContainer(CometContainer container){
        this.container = container;
    }
    
    
}
