/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comet.server.http;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Unni Vemanchery Mana
 */
public class URLMapper {

    private String resource;
    
    private HttpRequestManager requestManager;
    
    private Map<String, Object> servletMapper;
    
    private String context ;
    
    private String key;
    
    private String servletPath;
    
    private String pathInfo;
    
    private String pathTransalated;
    
    public URLMapper(String context, String contextURI, HttpRequestManager requestManager){
      this.resource       = contextURI;    
      this.requestManager = requestManager;
      this.context        = context;
    }
    
    /*
     * path here is anything after context ie servlet path.
     * For ex: /servlet/Hello.
     */
    public URLMapper(String context, String path, Map<String, Object> servletMapping){
      this.resource = path;
      servletMapper = servletMapping;
      this.context = context;
    }
    
    public URLMapper(){
    }
    
    public boolean validateURL(){
      Iterator<String> iter = servletMapper.keySet().iterator();
      while(iter.hasNext()){
        String mappingKey = iter.next();
        String urlPattern = (String) servletMapper.get(mappingKey);
        if(isValidMapping(urlPattern)){
            System.out.println("---- what is the value of mapping key --- " + mappingKey);
            setKey(mappingKey);
            return true;
        }
      }
      return false;
    }
    
   private boolean isValidMapping(String urlPattern){
     String validUrl = null;
     if(urlPattern.contains("/*")){ // if the urlpattern has end of /*
           urlPattern = urlPattern.replaceAll("\\*", "");
      //     System.out.println("1111" + urlPattern);
           validUrl = context + urlPattern;
      //     System.out.println(" validUrl " + validUrl);
      //     System.out.println(" contextURI " + resource);
           String newResource = resource + "/";
           if(newResource.contains(urlPattern)){
              pathInfo = newResource.substring(newResource.indexOf(urlPattern)+ urlPattern.length(), newResource.length());
              translatePathToAbsolute();
              if(getPathInfo() == null || getPathInfo().trim().length() <=0){ // there is no path info present in the resource
                setServletPath(resource); // setting servlet path 
              }else {
                setServletPath(newResource.substring(0, newResource.indexOf(urlPattern) + urlPattern.length()));
              }
              return true;
           }    
     }else if(urlPattern.contains("*.")){ // if the urlpattern has a *.
            urlPattern = "." + urlPattern.replaceAll("\\*.", "");
       //     System.out.println("2222" + urlPattern);
            String newResource = resource;
       //     System.out.println("....newResource... " + newResource);
            setServletPath(newResource);
            if(resource.endsWith(urlPattern)) return true;
     }else if(!urlPattern.endsWith("/")){ // if the urlPattern does not have a ending /
       //  System.out.println("33");
         validUrl = context + urlPattern;
       //  System.out.println(" validUrl " + urlPattern);
         String newResource = context + resource; 
        // System.out.println(" resource " + resource);
         setServletPath(resource);
         if(resource.equals(urlPattern)){
         //   System.out.println(" ------ resource matched-------");
            return true;
         }    
     }else if(urlPattern.endsWith("/")){  // this is the default case
           // System.out.println("4444");
            validUrl = this.requestManager.getResource().getContext() + "/" + urlPattern;
           // System.out.println(" validUrl " + validUrl);
            setServletPath(resource);
            if(resource.startsWith(validUrl)) return true;
     }
     return false;
   }
   
   private void translatePathToAbsolute(){
     File f = new File(System.getProperty("user.dir") + File.separator + com.comet.utils.Constants.application + File.separator + context + File.separator + pathInfo);
     pathTransalated = f.getAbsolutePath();
   }
   
    public static void main(String []arg){
        URLMapper mapper = new URLMapper();
      //  System.out.println(mapper.isValidMapping("/catalog"));
        String url = "/servlets/servlet/RequestInfoExample/";
        String resource = "/servlets/servlet/RequestInfoExample/a/b;c/";
        System.out.println(resource.contains(url));
        if(resource.contains(url)){
          resource = resource.substring(0,resource.indexOf(url) + url.length());
          System.out.println(" resource " + resource);
        }
    }

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return the servletPath
     */
    protected String getServletPath(){
      return servletPath;
    }

    /**
     * @param servletPath the servletPath to set
     */
    private void setServletPath(String servletPath){
      this.servletPath = servletPath;
    }

    /**
     * @return the pathInfo
     */
    public String getPathInfo(){
      return pathInfo;
    }

    /**
     * @return the pathTransalated
     */
    public String getPathTransalated(){
       return pathTransalated;
    }
}
