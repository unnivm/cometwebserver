/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comet.core;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author unni_vm
 */
public class CometState {
    
    private static CometState ct = new CometState();
    
    // for storing compressed sessioncontext along with context ID
    private Map<String, SessionContext> map = new HashMap<String, SessionContext>();
    
    private SessionContext sc;
    
    private String isURLReWrite ;
    
    private CometState(){
    }
    
    public void store(String key, SessionContext value){
      map.put(key, value);
    }
    
    public Map<String, SessionContext> getValue(){
      return map;
    }
    
    public static CometState getCometState(){
      return ct;    
    }
    
    public void setSerialializedSession(SessionContext sc){
       this.sc = sc;
    }
    
    public void setSerialializedSession(Map<String,SessionContext> sc){
        this.map = sc;
    }
    
    public SessionContext getSerialializedContext(){
       return sc; 
    }
    
    public Map<String,SessionContext> getSerialializedSessionContextMap(){
        return map;
    }

    /**
     * @return the isURLReWrite
     */
    public String getIsURLReWrite() {
        return isURLReWrite;
    }

    /**
     * @param isURLReWrite the isURLReWrite to set
     */
    public void setIsURLReWrite(String isURLReWrite) {
        this.isURLReWrite = isURLReWrite;
    }
    
}
