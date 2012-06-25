/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comet.utils;

 import java.io.File;
 import java.util.Date;
 import java.util.HashMap;
 import java.util.Map;

 import com.comet.servlet.classloaders.util.ClassLoaderUtil;

 public class FileMonitor implements ITask{

	 private Map<String,String> fileMap = new HashMap<String,String>();
	 
	 private static final String root   = "C:/eclipse/Comet/src/com/server/utils";
	 
	 private static File dir            = new File(root); 
	 
	 private File [] files 				= dir.listFiles();
	 
	 	 
	 public FileMonitor(){
		readFilesAttribute();
		//new Schedule(0,this);
	 }
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FileMonitor fileMonitor = new FileMonitor();
		fileMonitor.readFilesAttribute();
	}
	
	/**
	 * 
	 */
    @Override
	public boolean execute() {
		monitorFiles();
		return true;
	}

	/**
	 * 
	 * @return
	 */
	public Map<String, String> getFileMap() {
		return fileMap;
	}

	/**
	 * 
	 * @param fileMap
	 */
	public void setFileMap(Map<String, String> fileMap) {
		this.fileMap = fileMap;
	}
	
	/**
	 * @Description : This method reads a files last modified time 
	 * 				  and stores into hash map
	 */
	private void readFilesAttribute(){
		for(File f:files){
			if(f.isFile())	
			fileMap.put(f.getName(), new Date(f.lastModified()).toString());
		}
	}
	
	public void monitorFiles(){
		for(File f:files){
			if(f.isFile()){	
			String timeStamp = fileMap.get(f.getName());
				if(!timeStamp.equalsIgnoreCase(new Date(f.lastModified()).toString())) {
					System.out.println(" matched ");
					fileMap.put(f.getName(), new Date(f.lastModified()).toString());
						 try {
							ClassLoaderUtil.loadTheClass("com.server.utils.Test").newInstance();
						} catch (InstantiationException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}catch (ClassNotFoundException e) {
							 e.printStackTrace();
						 }
				}
			}	
		}
	}

}
