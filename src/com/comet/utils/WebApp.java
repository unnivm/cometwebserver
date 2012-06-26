/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comet.utils;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;
import java.util.Map;

 public class WebApp {

	 private static final String root  = "C:/jakarta-tomcat-5.5.9/webapps";
	 
	 private static File dir  =  new File(root);

	 private final static String webInf = "WEB-INF/classes";
	 
	 private static Map<String,String> classmap = new HashMap<String,String>();
	 
	 public static void main(String []arg){
		 WebApp webApp = new WebApp();
		 File [] f = webApp.listAllProjects();
		 for(File f1:f){
			System.out.println(" Directory name "+f1.getName());
			File classFile = new File(root+"/"+webInf);
			FileFilter fileFilter = new FileFilter() {
                @Override
				public boolean accept(File file) {
					return file.isDirectory();
				}
			};
			File [] dirs = classFile.listFiles();
		 }
	 }
	 
	 /**
	  * @Description: This method lists all the directories in the WEBAPP folder
	  * @return array of file
	  */
	 private File[] listAllProjects(){
		 FileFilter fileFilter = new FileFilter(){
            @Override
			 public boolean accept(File file){
				 return file.isDirectory();
			 }
		 };
		 return dir.listFiles(fileFilter);
	 }
	 
 }
