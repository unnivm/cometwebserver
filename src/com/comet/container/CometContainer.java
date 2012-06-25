/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 package com.comet.container;


import com.comet.core.CometShutdown;
import com.comet.core.CometState;
import com.comet.core.SessionContext;
import com.comet.server.http.HttpHeader;
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
import com.comet.utils.FileUtil;
import com.comet.utils.XMLUtil;
import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.InflaterInputStream;

 public final class CometContainer {
	  private HttpServletResponseImpl httpServletResponseImpl;
	  private HttpServletRequestImpl httpServletRequestImpl;
	  private HttpRequestManager httpRequestManager;
          private CometState cometState;
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
          private static final Logger logger = Logger.getLogger(CometContainer.class.getName());
          
          private File file;
          private ClassLoader cl;
          private URL url;
          private URL[] urls;
          private HashMap<String, Class> classes = new HashMap<String, Class>();
    
          private Request  request;
          private Response response;
          private ByteBuffer contentBuffer;
          
          private String data;
          
          private Application app;
          private String appName = null;
          
          private String currDir = System.getProperty("user.dir") + File.separator + Constants.application;
	 
          public CometContainer(HttpRequestManager httpRequestManager,CometState cometState){
	    this.httpRequestManager = httpRequestManager;
            this.cometState = cometState;
	    init();
            CometShutdown csd = new CometShutdown(this);
            Runtime.getRuntime().addShutdownHook(csd);
	  }
          public CometContainer(){
              
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

  public Object processContent(){
     CharSequence strContent = null;
     StringBuilder sb = new StringBuilder();
     HttpHeader hh    = new HttpHeader();
     if(loadHttpServlet()){
        strContent = httpServletResponseImpl.getData();
        if(getRequest().getSessionCookie() != null) {
           System.out.println(" what is the cookie value coming here " + getRequest().getSessionCookie().getValue()); 
          httpServletResponseImpl.addCookie(getRequest().getSessionCookie());
        }
        hh.setContent(strContent);
        hh.setContentType(httpServletResponseImpl.getContentType() == null ? Constants.html : httpServletResponseImpl.getContentType());
        sb.append(hh.constructHttpResponse(200, httpServletResponseImpl));
        sb.append(strContent);
        return sb;
    }
    WebResource resource = httpRequestManager.getResource();
    if(resource.getResource().endsWith(".htm") || resource.getResource().endsWith(".html")){
        strContent = FileUtil.readFile(resource.getContext() + "/" + resource.getResource());
        hh.setContent(strContent);
        hh.setContentType(com.comet.server.http.Constants.html);
        sb.append(hh.constructHttpResponse(200));
        sb.append(strContent);
        return sb;
    } 
    if(resource.getResource().endsWith(".png") || resource.getResource().endsWith(".gif") || resource.getResource().endsWith(".jpg")){
       byte[] b = FileUtil.loadResource(resource.getContext() + "/" + resource.getResource());
       ByteBuffer byteBuffer = ByteBuffer.wrap(b);
       return byteBuffer;
    }
       // System.out.println(".... It is not a valid resource....");
        String error = "<html><head><title>Comet -1.0 - Error report</title><style><!--H1 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:22px;} H2 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:16px;} H3 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:14px;} BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;} B {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;} P {font-family:Tahoma,Arial,sans-serif;background:white;color:black;font-size:12px;}A {color : black;}A.name {color : black;}HR {color : #525D76;}--></style> </head><body><h1>HTTP Status 404 - /unni/servlets/servlet/HelloWorldExample</h1><HR size=\"1\" noshade=\"noshade\"><p><b>type</b> Status report</p><p><b>message</b> <u>/unni/servlets/servlet/HelloWorldExample</u></p><p><b>description</b> <u>The requested resource (/unni/servlets/servlet/HelloWorldExample) is not available.</u></p><HR size=\"1\" noshade=\"noshade\"><h3>Comet - 1.0</h3></body></html>";
        hh.setContent(error);
        hh.setContentType(com.comet.server.http.Constants.html);
        sb.append(hh.constructHttpResponse(404));
        sb.append(error);
        return sb; 
  }
    
    public boolean loadHttpServlet(){
        WebResource resource = httpRequestManager.getResource();
        String servlet       = null;
        Class clazz          = null;
        boolean isValid      = resource.processForValidResource(servlet);
        if(!isValid){
           return false;
        }
        servlet = resource.getResourceValue();
        String path = resource.getResourcePath();
       //logger.log(Level.INFO, " what is the loading servlet .... {0}", servlet);
        try {
                if (file == null){
                    file = new File(path);
                    url = file.toURI().toURL();
                    urls = new URL[]{url};
                }
                if (!path.equals(file.getAbsolutePath())){
                    file = new File(path);
                    url = file.toURI().toURL();
                    urls = new URL[]{url};
                }
                cl = new URLClassLoader(urls);
                clazz = (Class) classes.get(servlet);
                if (clazz == null){
                    clazz = cl.loadClass(servlet);
                }
                Object object2 = clazz.newInstance();
                Method[] methods = object2.getClass().getDeclaredMethods();
                httpServletResponseImpl = new HttpServletResponseImpl(getResponse());
                httpServletRequestImpl  = new HttpServletRequestImpl(getRequest(), contentBuffer);
                Object[] arguement = new Object[2];
                arguement[0] = httpServletRequestImpl;
                arguement[1] = httpServletResponseImpl;
                String httpMethod = resource.getMethod();
                Object object = "GET".equals(httpMethod) ? methods[0].invoke(object2, arguement) : methods[1].invoke(object2, arguement);
                classes.put(servlet, clazz);
            } catch (Exception e){
                logger.log(Level.SEVERE, e.toString());
                e.printStackTrace();
            }
            data = httpServletResponseImpl.getData();
        //    System.out.println(" ....servlet loaded......Returning.");
            return true;
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
	File f   = new File(projectPath);
	if(f.exists()){
	    listofDirs  = f.listFiles();
	    int size    = listofDirs.length;
            System.out.println(" what is the size " + size);
            if(size > 0){
	       application = new Application[size];
	       //displayFiles(listofDirs);
               System.out.println(" currdir " +  currDir);
               readFiles(f);
               parseWebDotXML();            
            }
            // read serialized data
            readSerializedData();
	}
  }
  
  private void  parseWebDotXML(){
      String pathToWebXML =  null;
      List<Document> doclist = new ArrayList<Document>();
      System.out.println(" appList " + appList);
      if(appList.size() > 0){
      for(Application apps:appList){
             if(apps !=null) {
		try {
                    File f = new File(projectPath + "\\" + apps.getName() + "\\WEB-INF\\"+apps.getWebxml()); 
                    if(f.exists()){
                       pathToWebXML = f.getCanonicalPath();
		       FileInputStream fis = new FileInputStream(pathToWebXML);
		       doclist.add(XMLUtil.loadXML(fis));
                    }
		} catch (FileNotFoundException e){
                    System.out.println(" web.xml is not found");
		} catch( IOException ioe){
                }
             }
	  }
      } 
       System.out.println(" doclist " + doclist);
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
   //  System.out.println(" key: "+key);
   //  System.out.println(" value: "+properties.getProperty(key));
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
			if(!f.isDirectory()){
			  if(webxml.equalsIgnoreCase(f.getName())){
                                  String prjName = listofDirs[counter].getName();
                                  System.out.println(" ...prjName... " + prjName+" "+counter);
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
 
 /*
  * This method recursively read all files under applications folder 
  * and sets all project folders and its web.xml to Application object. 
  */
 public void readFiles(File file){ 
  if(file.isDirectory()){
      if(currDir.equals(file.getParent())){
          System.out.println("[DIR] "  + file.getName());
          app = new Application();
          appName =  file.getName();
       }
  }else if(file.isFile()){
       if(webxml.equals(file.getName())){
          app.setName(appName); 
          app.setWebxml(file.getName());
          appList.add(app);
       }
  }    
   File[] children = file.listFiles();
     if(children != null){
        for(File child : children){ 
          readFiles(child); 
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

 public void setHttpRequestManager(HttpRequestManager httpRequestManager){
   this.httpRequestManager = httpRequestManager;
 }

    /**
     * @return the request
     */
    public Request getRequest(){
        return request;
    }

    /**
     * @param request the request to set
     */
    public void setRequest(Request request){
        this.request = request;
    }

    /**
     * @return the response
     */
    public Response getResponse(){
        return response;
    }

    /**
     * @param response the response to set
     */
    public void setResponse(Response response) {
        this.response = response;
    }

    /**
     * @param contentBuffer the contentBuffer to set
     */
    public void setContentBuffer(ByteBuffer contentBuffer) {
        this.contentBuffer = contentBuffer;
    }

    /**
     * @return the data
     */
    public String getData() {
        return data;
    }

    private void readSerializedData(){
    try {
            FileInputStream fis = new FileInputStream("session.ser");
            try {
                ObjectInputStream ois = new ObjectInputStream(fis);
                try {
                    SessionContext sc = (SessionContext) ois.readObject();
                    cometState.setSerialializedSession(sc);
                    ois.close();
                    fis.close();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(CometContainer.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (IOException ex) {
                Logger.getLogger(CometContainer.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CometContainer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

