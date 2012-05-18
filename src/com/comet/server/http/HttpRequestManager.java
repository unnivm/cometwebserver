/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comet.server.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import com.comet.servlet.impl.Request;
import com.comet.servlet.impl.Response;
import com.comet.utils.HttpUtils;
import com.comet.container.ContainerContext;

 public class HttpRequestManager {

    private List<String> httpReqList = new ArrayList<String>();
	
    private boolean isContextPath = true;
    	
    private Map<String,String> headerMap = new HashMap<String,String>();
    
    private boolean loadServlet=false;
    
    private static Map<String,Object> projectmap;
    
    private static List<String> projects  = new ArrayList<String>();
    
    private WebResource resource;
    
   private ContainerContext cc;
   
   private Request request ;
   
	public HttpRequestManager() {
            resource = new WebResource(this);
	}
	
	public void setHttpReqList(List<String> httpReqList){
		this.httpReqList = httpReqList;	
	}
	
	public List<String> getHttpReqList(){
		return httpReqList;	
	}
	
	public synchronized void addData(String str){
		httpReqList.add(str);
	}
	
 public final synchronized void processHeaderDataFromRequest(final ByteBuffer buffer,final Request request,final Response response,final SocketChannel channel){
       try{
           this.request = request;
          //  resource = new WebResource(this);
	    buffer.flip();
	    Charset charset = Charset.forName("US-ASCII");
	    CharsetDecoder decoder = charset.newDecoder();
	    CharBuffer cb = decoder.decode(buffer);
	    System.out.println(" header data "+cb.toString());
	    StringTokenizer st = new StringTokenizer(cb.toString(),":");
   	    String str = st.nextToken();
	    StringTokenizer st2 = new StringTokenizer(str," ");
	    String strMethod = st2.nextToken();
	    String strContextpath = st2.nextToken();
	    System.out.println(" what is the strContextpath "+strContextpath);
	    String strProtocol =  st2.nextToken();
	    System.out.println("strProtocol....... " + strProtocol);
	    if(strContextpath.length() <=1) setContextPath(false);
	    else setContextPath(true);
	    httpReqList.clear();
	    httpReqList.add(strMethod); 	  // GET or POST
	    httpReqList.add(strContextpath); // context path
	    httpReqList.add(strProtocol); // Protocol
            resource.setContext(strContextpath);
            resource.setProtocol(strProtocol);
            resource.setMethod(strMethod);
 	     // processing the query String
	     processQueryString(strContextpath,request);
             processHeaderNames(request,cb.toString());
	    // setting local address
	     setLocalAddress(request,channel);
	     setRemoteAddress(request,channel); 
	     request.setContentLength((cb==null)?-1:cb.toString().length());
	     setContentType(request);
	     setLocalName(request,channel);
	     BufferedReader reader = new BufferedReader(new InputStreamReader(channel.socket().getInputStream()));
	     request.setBufferedReader(reader);
	     setOutputStream(response, channel);
             setRequestURI(request,strContextpath);
             setPathInfo(request,strContextpath);
	}catch(Exception e){
	}
     }

   public boolean isContextPath(){
	return isContextPath;
   }

   public void setContextPath(boolean isContextPath) {
	this.isContextPath = isContextPath;
   }
	
  private void printHeaderData(StringTokenizer st){
  try{
	 while(st.hasMoreTokens()){
	   System.out.println(" content "+st.nextToken());
	 }
     }catch(Exception e){
    	 e.printStackTrace();
     }
  }
	
	
  public void getCookieInfo(CharBuffer cb){
     String header = cb.toString();
     StringTokenizer st = new StringTokenizer(header, "\r\n");
     System.out.println("========================== ");
     printHeaderData(st);
     System.out.println("=====   End==========================");
  }
	
 /**
 * 
 * @param contextPath
 */
 private void processQueryString(String contextPath,Request request){
   Map<String,Object> requestMap = HttpUtils.processQueryString(contextPath);
   System.out.println(" what is there in the requestMap...."+requestMap);
//   request.setParameter(requestMap);
   request.setProtocol(getResource().getProtocol());
   Set keys = requestMap.keySet();
   Vector v = new Vector(keys);
   request.setParameterNames(v.elements());
 }
	
private void setLocalAddress(final Request request,final SocketChannel channel) throws UnknownHostException {
   request.setLocalAddress(channel.socket().getInetAddress().getLocalHost().getHostAddress());
}
	
private void setRemoteAddress(final Request request,final SocketChannel channel) {
   request.setRemoteAddress(channel.socket().getInetAddress().toString());
}
  
    private void setContentType(final Request request) {
     String token = headerMap.get("Accept");
     String [] type = token.split(",");
     request.setContentType(type[0]);
    }

    private void setLocalName(final Request request,final SocketChannel channel) throws UnknownHostException {
    	request.setLocalName(channel.socket().getInetAddress().getLocalHost().getHostName());
    }

    private void setLocalPort(final Request request,final String aToken){
      if(aToken.startsWith("Host:")){
         System.out.println(" aToken " + aToken); 
         request.setLocalPort(aToken.split(":")[2]);
      }
    }
    
    private void setOutputStream(final Response response, final SocketChannel channel ) throws IOException{
	response.setOutputStream(channel.socket().getOutputStream());
    }

    public Map<String, Object> getProjectmap(){
	return projectmap;
    }

   public void setContainerContext(ContainerContext cc){
       this.cc = cc;
   }

   public ContainerContext getContainerContext(){
       return cc;
   }
    /**
     * @return the resource
     */
    public WebResource getResource() {
        return resource;
    }
   
    private void setRequestURI(Request request,String uri){
       request.setRequestURI(uri);
    }
    
    private void setPathInfo(Request request,String pathInfo){
       request.setPathInfo(pathInfo); 
    }
    
    private void processHeaderNames(Request request, String header){
     StringTokenizer st3 = new StringTokenizer(header,"\r\n");
     String token = null;
     while(st3.hasMoreTokens()){
	   token = st3.nextToken();
            if(token.indexOf("=") != -1){
                  httpReqList.add(token); 
             }
             else if(!token.startsWith("Host:")){
	        		 String [] splittoken =  token.split(":");
	        		 System.out.println("splittoken[0] " + splittoken[0]);
	        		 if(!splittoken[0].equals(token)) {
	        		  headerMap.put(splittoken[0], splittoken[1]);
	        		 }
	     }else {
	        		// System.out.println(" host block...."+token);
	        		 httpReqList.add(token); // Host detail from which this request was received
	     }
     }
     request.setHeaderNames(st3);           
     setLocalPort(request,token);
     request.setProtocol(getResource().getProtocol());
     String param = httpReqList.get(httpReqList.size() - 1);
     String [] params = null;
     Map<String, Object> map = new HashMap<String, Object>();
     if(param != null){
     if(param.indexOf("&")  != -1){
        params = param.split("&");
        for(String s : params){
           map.put(s.split("=")[0], s.split("=")[1]);
        }
     }else{
        params = param.split("=");
        map.put(params[0],params[1]);
      }
     }
     // processing GET request if any
     map.putAll(HttpUtils.processQueryString(resource.getContext()));
     System.out.println(" ...final map.... " + map);
     request.setParameter(map);
     request.setParameterNames(new Vector(map.keySet()).elements());
    }
    
    public Request getRequest(){
        return request;
    }
 }