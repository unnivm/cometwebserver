/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 package com.comet.container;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.comet.server.http.HttpRequestManager;
import com.comet.server.http.WebResource;
import com.comet.servlet.impl.HttpServletRequestImpl;
import com.comet.servlet.impl.HttpServletResponseImpl;
import com.comet.servlet.impl.Request;
import com.comet.servlet.impl.Response;
import com.comet.utils.Constants;
import com.comet.utils.XMLUtil;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

 public final class CometContainer {
	  private HttpServletResponseImpl httpServletResponseImpl;
	  private HttpServletRequestImpl httpServletRequestImpl;
	  private HttpRequestManager httpRequestManager;
	  private Object result;
	  private String root = "/" + Constants.application;  // this folder is used to keep or deploy the projects
	  private Map<String, Object> projectmap = new HashMap<String,Object>();
	  private  Application [] application = null;
	  private int counter=0;
	  File [] listofDirs = null;
	  private final String webxml="web.xml";
	  private final String jars="jar";
	  private List<String> listjars= new ArrayList<String>();
	  private String projectPath = null;
	  private List<Application> appList = new ArrayList<Application>();
          
	  public CometContainer(HttpRequestManager httpRequestManager) {
	     this.httpRequestManager = httpRequestManager;
	     init();
	  }
          
   public  Object loadHttpServlet(String servlet){
    try{
			    Object  object2  =  ClassLoader.getSystemClassLoader().loadClass("com.servlet.test."+servlet).newInstance();
				Method [] methods = object2.getClass().getDeclaredMethods();
				Object [] obj1 = new Object[2];
				obj1[0] = httpServletRequestImpl;
				obj1[1] = httpServletResponseImpl;
				methods[0].setAccessible(true);
				result = methods[0].invoke(object2, obj1);
			  }catch(Exception e){
			  }
			  return result;
    }

   private boolean loadHttpServlet(Request request, Response response, ByteBuffer contentBuffer) {
   WebResource resource = httpRequestManager.getResource();
   String servlet = null;
   if(resource.processForValidResource(servlet)){
      servlet = resource.getResourceValue();
      String path = resource.getResourcePath();
      System.out.println(" what is the loading servlet .... " + servlet);
      System.out.println(" what is the loading servlet path.... " + path);
      try{
            File file = new File(path);
            URL url = file.toURI().toURL();
            URL [] urls = new URL[]{url};
            ClassLoader cl = new URLClassLoader(urls);
            Object object2 = cl.loadClass(servlet).newInstance();
	    Method [] methods = object2.getClass().getDeclaredMethods();
	    httpServletResponseImpl = new HttpServletResponseImpl(response);
	    httpServletRequestImpl = new HttpServletRequestImpl(request,contentBuffer);
	    Object [] arguement = new Object[2];
	    arguement[0] = httpServletRequestImpl;
	    arguement[1] = httpServletResponseImpl;
	    String httpMethod = resource.getMethod();
            Object object = "GET".equals(httpMethod)? methods[0].invoke(object2, arguement): methods[1].invoke(object2, arguement);
	}catch(Exception e){
          return false;
	}
        return true;
       } 
       return false;
 }
	public Object loadNonHttpServlet(String contextpath,String uri) {
		 if(uri.indexOf(".jsp") != -1) {
			 // load jsps;
		 }else { // load other resource
		    
		 }
		 return null;
	}
	 
	public HttpServletResponseImpl getHttpServletResponseImpl() {
		return httpServletResponseImpl;
	}

	public void setHttpServletResponseImpl(
			HttpServletResponseImpl httpServletResponseImpl) {
		this.httpServletResponseImpl = httpServletResponseImpl;
	}

	public HttpServletRequestImpl getHttpServletRequestImpl() {
		return httpServletRequestImpl;
	}

	public void setHttpServletRequestImpl(
			HttpServletRequestImpl httpServletRequestImpl) {
		this.httpServletRequestImpl = httpServletRequestImpl;
	}
	public Object loadIncludedURI(String path){
		Object obj = loadHttpServlet(path);
		httpServletResponseImpl.sendData((String) obj);
		return obj;
	}
	private Document [] loadWebXML() {
	Document [] document = null;
	return document;
	}
	
	private List<String> getContextPath(String projectpath){ 
		 List<String> list = new ArrayList<String>();
		 return list;
	}

  /*
   * This method will load all projects under application
   * folder which is considered as the root of web applications
   * 
   */
  private void initialize(){
	projectmap  = new HashMap<String, Object>();
	File file   = new File(projectPath);
	if(file.exists()){
	    listofDirs  = file.listFiles();
	    int size    = listofDirs.length;
            if(size > 0){
	       application = new Application[size];
	       displayFiles(listofDirs);
               parseWebDotXML();            
            }
	}
  }
	
  private void loadWebResource() {
	  int appsize;
	  WebXML webxml;
	  Iterator iterator;
	  Iterator srvltIterator;
	  int counter=0;
	  // getting number of applications under webapps
	  appsize = (projectmap==null)?0:projectmap.size();
	  Servlet [] servlet = new Servlet[appsize];
	  if(appsize>0){
		  iterator= projectmap.keySet().iterator();
		  while(iterator.hasNext()) {
			  String projectkey = (String) iterator.next();
			  System.out.println(" key "+projectkey);
			  if(projectkey !=null) {
				  webxml = (WebXML)projectmap.get(projectkey);
				  Map<String,Object> smap = webxml.getServlet();
				  srvltIterator = smap.keySet().iterator();
				  while(srvltIterator.hasNext()) {
					  String servletname =(String) srvltIterator.next();
					  servlet[counter] = new Servlet();
					  servlet[counter].setName(servletname);
					  //servlet[counter].setPath()
				  }
			  }
		  }
	  }
  }
  
  private void  parseWebDotXML(){
      String pathToWebXML =  null;
      List<Document> doclist = new ArrayList<Document>();
      if(application.length >1){
      for(Application apps:application){
             if(apps !=null) {
		try {
                 pathToWebXML = new File(projectPath + "\\" + apps.getName() + "\\WEB-INF\\"+apps.getWebxml()).getCanonicalPath();
		 FileInputStream fis = new FileInputStream(pathToWebXML);
		 doclist.add(XMLUtil.loadXML(fis));
		} catch (FileNotFoundException e){
                    System.out.println(" web.xml is not found");
		} catch( IOException ioe){
                }
             }
	  }
      } 
       if(doclist.size()>=1){
	 parsewebDotXML(doclist);
         System.out.println( "project map: " + projectmap);
       }
       
  }
  
  private void parsewebDotXML(final List<Document> doclist) {
	  Map<String,Object> servletmap = new HashMap<String, Object>();
	  Map<String,Object> servletpatternmap = new HashMap<String, Object>();
	  NodeList mappinglist;
	  int mappingsize ;
	  int appsize = 0;
	  for(Document doc: doclist){
		  Application app = appList.get(appsize);
		  NodeList nodelist = XMLUtil.getNodeList(doc, "servlet");
		  int size = (nodelist==null)?0:nodelist.getLength();
		  for(int i=0; i<size; i++){
		  Element element = (Element) nodelist.item(i);
		  if(element !=null) {
			  NodeList srvltNameList  = element.getElementsByTagName("servlet-name");
			  NodeList srvltClassList = element.getElementsByTagName("servlet-class");
			  Element servletName 	  = (Element) srvltNameList.item(0);
			  Element servletClass    = (Element) srvltClassList.item(0);
			  servletmap.put(servletName.getFirstChild().getNodeValue(), servletClass.getFirstChild().getNodeValue());
		  }
		 }
		  WebXML xml = new WebXML();
		  xml.setServlet(servletmap);
		  mappinglist = XMLUtil.getNodeList(doc, "servlet-mapping");
		  mappingsize = (mappinglist==null)?0:mappinglist.getLength();
		  for(int j=0; j<mappingsize; j++) {
			  Element mappingElmt = (Element) mappinglist.item(j);
			  if(mappingElmt != null){
				  NodeList srvltNameList  = mappingElmt.getElementsByTagName("servlet-name");
				  NodeList urlPatternList  = mappingElmt.getElementsByTagName("url-pattern");
				  Element servletName 	  = (Element) srvltNameList.item(0);
				  Element pattern 	 	  = (Element) urlPatternList.item(0);
				  servletpatternmap.put(servletName.getFirstChild().getNodeValue(), pattern.getFirstChild().getNodeValue());
			  }
		  }
		  xml.setServletMapping(servletpatternmap);
		  projectmap.put(app.getName(), xml);
		  appsize++;
	  }
          ContainerContext cc = new ContainerContext();
          cc.setProjectMap(projectmap);
          if(httpRequestManager != null)
          httpRequestManager.setContainerContext(cc);
  }
  
	
 public void init(){
    displaySystemProperties();
    setProjectPath();
    initialize();
 }

 private void displaySystemProperties(){
  Properties properties = System.getProperties();
  Enumeration enumeration = properties.elements();
  while(enumeration.hasMoreElements())
  {
     String key = (String) enumeration.nextElement();
     System.out.println(" key: "+key);
     System.out.println(" value: "+properties.getProperty(key));
  }
     System.out.println(" java class path "+System.getProperty("java.class.path"));
 }
 
 private void setProjectPath(){
        try {
            projectPath = new File(System.getProperty("user.dir") + root).getCanonicalPath();
            System.out.println(" projectPath " + projectPath);
        }catch (IOException ex){
            Logger.getLogger(CometContainer.class.getName()).log(Level.SEVERE, null, ex);
        }
 }
 
 private void displayFiles(File [] files){
  for(File f: files){
			if(!f.isDirectory()) {
			  if(webxml.equalsIgnoreCase(f.getName())){
                                  String prjName = listofDirs[counter].getName();
                                  System.out.println(" ...prjName... " + prjName);
				 // projectmap.put(prjName, f.getName());
                                  Application app = new Application();
				  app.setWebxml(f.getName());
				  app.setName(prjName);
				  app.setJars(listjars);
                                  appList.add(app);
       				  application[counter] = app;
				  listjars = new ArrayList<String>();
				  counter++;
			  }
			  else if(f.getName().indexOf(jars) != -1) {
			    listjars.add(f.getName());
			  }
			} else{
                            displayFiles(f.listFiles());
                        }
     }
 }
 
 
 public Map<String, Object> getProjectmap() {
		return projectmap;
 }

 public void setProjectmap(Map<String, Object> projectmap) {
		this.projectmap = projectmap;
 }

 public  Application[] getApplication() {
		return application;
 }

 public void setApplication(Application[] application) {
		this.application = application;
 }
public HttpRequestManager getHttpRequestManager() {
	return httpRequestManager;
}
public void setHttpRequestManager(HttpRequestManager httpRequestManager) {
	this.httpRequestManager = httpRequestManager;
}
	
}

