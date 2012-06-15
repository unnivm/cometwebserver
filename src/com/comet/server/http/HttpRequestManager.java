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
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import com.comet.servlet.impl.Request;
import com.comet.servlet.impl.Response;
import com.comet.utils.HttpUtils;
import com.comet.container.ContainerContext;
import com.comet.utils.StringUtils;
import java.nio.charset.CharacterCodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

 public final class HttpRequestManager {

    private List<String> httpReqList = new ArrayList<String>();
	
    private boolean isContextPath = true;
    	
    private Map<String,String> headerMap = new HashMap<String,String>();
    
    private boolean loadServlet=false;
    
    private static Map<String,Object> projectmap;
    
    private static List<String> projects  = new ArrayList<String>();
    
    private WebResource resource;
    
    private ContainerContext cc;
   
    private static final Logger logger = Logger.getLogger(HttpRequestManager.class.getName());
   
    private Charset charset;
    
    private CharsetDecoder decoder;
    
    private Request request;
   
    public HttpRequestManager(){
      resource = new WebResource(this);
      initializeCharBuffer();
    }
	
	public void setHttpReqList(List<String> httpReqList){
	  this.httpReqList = httpReqList;	
	}

   public void initializeCharBuffer(){
      charset = Charset.forName("US-ASCII");
      decoder = charset.newDecoder();
   }        

    public  void processHeaderDataFromRequest1(final ByteBuffer buffer,final Request request,final Response response,final SocketChannel channel){
      this.request = request;
      buffer.flip();
      CharBuffer cb = null;
      try {
        cb = decoder.decode(buffer);
        logger.log(Level.INFO, " header data {0}", cb.toString());
      } catch (CharacterCodingException ex){
        Logger.getLogger(HttpRequestManager.class.getName()).log(Level.SEVERE, null, ex);
      }
      StringTokenizer st = new StringTokenizer(cb.toString(),"\r\n");
      String reqResource = st.nextToken(); //GET /unni/servlets/images/return.gif HTTP/1.1
      String [] reqResources = reqResource.split(" ");
      String contextPath = reqResources[1];
      long start = System.currentTimeMillis();
      headerMap = HttpUtils.processRequestHeader(st);
      long end = System.currentTimeMillis();
      System.out.println(" time taken to process request " + (end - start));
      System.out.println("=====");
      System.out.println("header Map " + headerMap);
      start = System.currentTimeMillis();
      resource.setContext(contextPath);
      resource.setProtocol(reqResources[2]);
      resource.setMethod(reqResources[2]);
      setMethod(request, contextPath);
      request.setProtocol(reqResources[2]);
      setRequestURI(request, contextPath);
      setPathInfo(request, contextPath);
      setLocalPort(request);
      setRequestParams(request, reqResources[1]);
      try{
          setLocalAddress(request,channel);
          setRemoteAddress(request,channel);
          request.setContentLength((cb==null)?-1:cb.toString().length());
      }catch (UnknownHostException ex) {
        Logger.getLogger(HttpRequestManager.class.getName()).log(Level.SEVERE, null, ex);
      }
      BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(channel.socket().getInputStream()));
            request.setBufferedReader(reader);
            setOutputStream(response, channel);
        }catch (IOException ex){
          Logger.getLogger(HttpRequestManager.class.getName()).log(Level.SEVERE, null, ex);
        }
       setContextPath(request);
       setAuthType(request);
       setCookie(request);
       end = System.currentTimeMillis();
       System.out.println(" time taken to process the remainign operation " + (end - start));
    }
    
   public boolean isContextPath(){
	return isContextPath;
   }

   public void setContextPath(boolean isContextPath) {
	this.isContextPath = isContextPath;
   }
	
 /**
 * 
 * @param contextPath
 */
 private void processQueryString(String contextPath,Request request){
   Map<String,Object> requestMap = HttpUtils.processQueryString(contextPath); // processing GET requests
   System.out.println(" what is there in the requestMap...."+requestMap);
   Set keys = requestMap.keySet();
   request.setParameterNames(new Vector(keys).elements());
 }
	
private void setLocalAddress(final Request request,final SocketChannel channel) throws UnknownHostException {
   request.setLocalAddress(channel.socket().getInetAddress().getLocalHost().getHostAddress());
}
	
private void setRemoteAddress(final Request request,final SocketChannel channel) {
   request.setRemoteAddress(channel.socket().getInetAddress().toString());
}
  
private void setContentType(final Request request){
  String token = headerMap.get("Accept");
  System.out.println(" token " + token);
  if( token != null ){
      String [] type = token.split(",");
      request.setContentType(type[0]);
   }
}

    private void setLocalName(final Request request,final SocketChannel channel) throws UnknownHostException {
    	request.setLocalName(channel.socket().getInetAddress().getLocalHost().getHostName());
    }

    private void setLocalPort(final Request request){
      String host = headerMap.get("Host").trim();
      System.out.println("localport " + host);
      request.setLocalPort(host.split(":")[1]);
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
   
    /*
     * This method sets request URI, query String and request URL.
     */
    private void setRequestURI(Request request,String uri){
       System.out.println(" what is the uri " + uri);
       System.out.println(" also what is the host " + httpReqList);
       if(uri.contains("?")){
         String reqURI = uri.substring(0,uri.indexOf("?"));
         System.out.println(" reqURI " + reqURI);
         request.setRequestURI(reqURI);
         request.setQueryString(uri.substring(uri.indexOf("?")+1, uri.length()));
         setRequestURL(request,reqURI);
       }
       else{
         request.setRequestURI(uri);
         // this method call is essential as previous query string value is not cleared.why?
         request.setQueryString(null); 
         setRequestURL(request,uri);
       }
    }
    
    private void setPathInfo(Request request,String pathInfo){
       request.setPathInfo(pathInfo); 
    }
    
   private void setRequestURL(Request req, String uri){
       String protocol = request.getProtocol();//httpReqList.get(2);
       System.out.println(" 1. protocol " + protocol);
       protocol = protocol.substring(0, protocol.indexOf("/")).toLowerCase();
       System.out.println(" 2. protocol " + protocol);
       //String host     = StringUtils.getSpecificStringFromList(httpReqList, "Host:");
       String host     = headerMap.get("Host");
       System.out.println(" 2. host " + host);
       StringBuffer sb = new StringBuffer();
       sb.append(protocol.trim()).append("://").append(host.trim()).append(uri.trim());
       System.out.println(" sb.toString " + sb.toString());
       req.setRequestURL(sb);
   }
    
    private void processHeaderNames(Request request, String header){
     StringTokenizer st3 = new StringTokenizer(header,"\r\n");
     String token = null;
     request.setHeaderNames(st3);
     while(st3.hasMoreTokens()){
	   token = st3.nextToken();
           httpReqList.add(token);
            if(token.contains(":")){
               String key   = token.substring(0, token.indexOf(":"));
               String value = token.substring(token.indexOf(":") + 1, token.length());
               headerMap.put(key, value);
            }
     }
     request.setHeaderMap(headerMap);
     setLocalPort(request);
     request.setProtocol(getResource().getProtocol());
     String param = httpReqList.get(httpReqList.size() - 1); // getting request params by POST method
     String [] params = null;
     Map<String, Object> map = new HashMap<String, Object>();
     if(param != null){
     if(param.indexOf("&")  != -1){
        params = param.split("&");
        for(String s : params){
          map.put(s.split("=")[0], s.split("=")[1]);
        }
     }else{
        if(param.contains("=")) {  
         params = param.split("=");
         map.put(params[0],params[1]);
        }
      }
     }
     // processing GET request if any
     map.putAll(HttpUtils.processQueryString(resource.getContext()));
     System.out.println(" ...final map.... " + map);
     System.out.println(" ...final header map.... " + headerMap);
     System.out.println(" ...final httpReqList.... " + httpReqList);
     request.setParameter(map);
     request.setParameterNames(new Vector(map.keySet()).elements());
    }
    
    private void setRequestParams(Request request, String contextPath) {
     Map<String, Object> requestMap = HttpUtils.processQueryString(contextPath);
     System.out.println(" what is there in the requestMap...." + requestMap);
     request.setHeaderMap(headerMap);
     String param = headerMap.get("post"); // processing post paramsters
     String [] params = null;
     if(param != null){
     if(param.indexOf("&")  != -1){
        params = param.split("&");
        for(String s : params){
          requestMap.put(s.split("=")[0], s.split("=")[1]);
        }
     }else{
        if(param.contains("=")) {  
         params = param.split("=");
         requestMap.put(params[0],params[1]);
        }
      }
     }
     request.setParameter(requestMap);
     Set keys = requestMap.keySet();
     request.setParameterNames(new Vector(keys).elements());
  }
    
   public Request getRequest(){
     return request;
   }
    
    public void setMethod(Request request, String method){
       request.setMethod(method);
    }
    
    private void setContextPath(Request request){
      request.setContextPath("/" + resource.getContext());
    }
    
    private void setAuthType( Request request ){
       request.setAuthType(headerMap.get("Authorization"));
    }
    
    private void setCookie( Request request ){
       if(headerMap.containsKey(com.comet.utils.Constants.Cookies)) {
           String value = headerMap.get(com.comet.utils.Constants.Cookies);
           request.setCookie(value);
       }
    }
    
    public static void main(String []arg){
        Map<String, String> mm = new HashMap<String, String>();
        mm.put("one" , "1");
        mm.put("two" , "2");
        System.out.println(" mm.values " + mm.values());
        Vector v = new Vector(mm.keySet());
        System.out.println(" v " + v);
     String header = "Host: localhost:8700\r\nUser-Agent: Mozilla/5.0 (Windows NT 6.1; rv:12.0) Gecko/20100101 Firefox/12.0"
                + "\r\nAccept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"
                + "\r\nAccept-Language: en-us,en;q=0.5"
                + "\r\nAccept-Encoding: gzip, deflate"
                + "\r\nConnection: keep-alive"
                + "\r\nReferer: http://localhost:8700/unni/servlets/index.html"
                + "\r\nCache-Control: max-age=0";
     StringTokenizer st3 = new StringTokenizer(header,"\r\n");
     String token = null;
     while(st3.hasMoreTokens()){
       token = st3.nextToken();
       System.out.println(" token " + token);
       if(token.contains(":")){
           String key   = token.substring(0, token.indexOf(":"));
           String value = token.substring(token.indexOf(":")+1, token.length());
           System.out.println("...key " + key);
           System.out.println("...value " + value);
           
       }
     }
    }
    
 }