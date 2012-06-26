/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comet.core;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author unni_vm
 */
public class CometState {
    
    private static CometState ct = new CometState();
    
    // for storing compressed sessioncontext along with context ID
    private Map<String, ByteBuffer> map = new HashMap<String, ByteBuffer>();
    
    private SessionContext sc;
    
    private CometState(){
    }
    
    public void store(String key, ByteBuffer value){
      map.put(key, value);
    }
    
    public Map<String, ByteBuffer> getValue(){
      return map;
    }
    
    public static CometState getCometState(){
      return ct;    
    }
    
    public void setSerialializedSession(SessionContext sc){
       this.sc = sc;
    }
    
    public SessionContext getSerialializedContext(){
       return sc; 
    }
    
}
