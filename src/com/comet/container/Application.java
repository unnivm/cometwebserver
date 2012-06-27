/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comet.container;

import com.comet.servlet.impl.CometServletContext;
import java.util.List;

 public class Application {
  
   private String name;
   private String path;
   private List<String> files;
   private List<String> packages;
   private List<String> myjars;
   private String webxml;
   private CometServletContext cometServletContext;
   
   public String getName(){
     return name;
   }
   public void setName(String name){
     this.name = name;
   }
   public String getPath(){
     return path;
   }
   public void setPath(String path){
     this.path = path;
   }
   public List<String> getFiles(){
     return files;
   }
   public void setFiles(List<String> files){
     this.files = files;
   }
   public List<String> getPackages(){
     return packages;
   }
   public void setPackages(List<String> packages){
     this.packages = packages;
   }
   public String getWebxml(){
     return webxml;
   }
   public void setWebxml(final String webxml){
    this.webxml = webxml;
   }
   public List<String> getJars() {
     return myjars;
   }
	public void setJars(List<String> myjars) {
		this.myjars = myjars;
	}
        
        @Override
        public String toString(){
            return "name: " + name+" path: "+path+" web.xml: "+webxml;
        }

    /**
     * @return the cometServletContext
     */
    public CometServletContext getCometServletContext() {
        return cometServletContext;
    }

    /**
     * @param cometServletContext the cometServletContext to set
     */
    public void setCometServletContext(CometServletContext cometServletContext) {
        this.cometServletContext = cometServletContext;
    }
 }

