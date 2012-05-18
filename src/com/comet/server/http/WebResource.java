/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comet.server.http;

import com.comet.container.WebXML;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Unni Vemanchery Mana
 */
public class WebResource {
    
    private String context;  // project name or application name
    
    private String resource; // anything after project name
    
    private HttpRequestManager requestManager;
    
    private String resourceValue;
    
    private String protocol;
    
    private String method;

    private String resourcePath;
    
    
    public WebResource(HttpRequestManager requestManager){
       this.requestManager = requestManager;  
    }
    
    public boolean isJSP(){
       return resource.contains(".jsp");
    }
    
    /**
     * @return the context
     */
    public String getContext(){
      return context;
    }

    /**
     * @param context the context to set
     */
    public void setContext(String context){
      String [] resources = context.split("/"); 
      if(resources != null && resources.length > 0){ 
         this.context = resources[1];  // sets the project name
         StringBuilder uri = new StringBuilder();
         for(int i = 2; i<= resources.length-1; i++){
            if(i != resources.length-1)
            uri.append(resources[i]).append("/");
            else uri.append(resources[i]); // constructs the resource string
         }
         this.resource = "/" + uri.toString(); 
      }
    }
   /**
     * @return the resource
     */
    public String getResource() {
      return resource;
    }

    /**
     * @param resource the resource to set
     */
    public void setResource(String resource){
      this.resource = resource;
    }

    /*
     * This method throws exception if requested resource has 
     * not found on server.If it is found, it is a valid
     * resource and can be processed.
     */
    public boolean processForValidResource(final String resource){
      return isValidResource(resource);
    }
    
    public boolean isValidResource(final String resource){
       Map<String, Object> prjMap = requestManager.getContainerContext().getProjectMap();
       Iterator<String> iterator  = prjMap.keySet().iterator();
       while(iterator.hasNext()){
        String key = iterator.next();
        WebXML webxml = (WebXML) prjMap.get(key);
        if(webxml != null){
           Map<String,Object> servletMap = webxml.getServletMapping();
           if(isValidResource(servletMap,webxml)) return true;
        }
       }
       return false;
    }
    
   private boolean isValidResource(Map<String, Object> mapper, final WebXML xml) {
        Iterator iterator = mapper.keySet().iterator();
        URLMapper urlmapper = new URLMapper(context, resource, mapper); //change this to singleton
        if(urlmapper.validateURL()){
            Map<String, Object> servlet = xml.getServlet();
            String key    = urlmapper.getKey();
            resourceValue = (String)servlet.get(key);
            System.out.println("...resourceValue " + resourceValue);
            resourcePath  = getPathToServlet((String)servlet.get(key));
            System.out.println("...resourcePath " + resourcePath);
            return true;
        }
        
        /*
        while(iterator.hasNext()){
         String key   = (String) iterator.next();
         String value = (String) mapper.get(key);
         if(value.trim().equals(resource.trim())){
            Map<String, Object> servlet = xml.getServlet();
            resourceValue = (String)servlet.get(key);
            resourcePath  = getPathToServlet((String)servlet.get(key));
            return true;
         }    
        }
          */
        return false;
   }

    /**
     * @return the resourceValue
     */
    public String getResourceValue() {
        return resourceValue;
    }

    /**
     * @return the protocol
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * @param protocol the protocol to set
     */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    /**
     * @return the method
     */
    public String getMethod() {
        return method;
    }

    /**
     * @param method the method to set
     */
    public void setMethod(String method) {
        this.method = method;
    }
    
    /*
     * This method is used to locate a servlet under WEB-INF/classes folder.
     */
    private String getPathToServlet(String resource){
     String path = null;   
     try {
        path = new File(System.getProperty("user.dir")+"/applications/"+context+"/WEB-INF/classes").getCanonicalPath();
        System.out.println(" path to servlet " + path);
     } catch (IOException ex){
       Logger.getLogger(WebResource.class.getName()).log(Level.SEVERE, null, ex);
     }
     return path;
    }

    /**
     * @return the resourcePath
     */
    public String getResourcePath() {
        return resourcePath;
    }
    
}
