/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comet.servlet.impl;

import java.io.BufferedReader;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.servlet.http.Cookie;

 public class Request {

	private  Map<String,Object> requestMap = new HashMap<String, Object>();
   
	private  Map<String,Object> requestObjMap = new HashMap<String, Object>();

	private  String protocol ;
	
	private String localAddress ;
	
	private String remoteAddress;
	
	private int contentLength;
	
	private String contentType;
	
	private Enumeration names;
	
	private String [] paramValues;
	
	private String encoding = "ISO-8859-1"; // http default encoding

	private String localName;
	
	private String port;

	private BufferedReader reader;
	
	private long dateHeader;
	
	private OutputStream  outputStream;
	
        private String method;
        
        private String requestURI;
        
        private String pathInfo;
        
        private Enumeration headerNames;
        
        private Cookie [] cookies;
        
        private Map<String,String> headerMap;
        
	public Request(){
	}
	
	public void setParameter(final Map<String,Object> requestMap) {
	  this.requestMap = requestMap;
	}
	
	public String getParameter(String key){
	  if(requestMap.get(key)==null) return null;
	  return requestMap.get(key).toString();
	}

	public Map<String,Object> getParameterMap() {
	  return requestMap;
	}
	
	public final void setAttribute(final String key, final Object value) {
	  requestObjMap.put(key, value);
	}
	
	public final Object getAttribute(final String key) {
	  return requestObjMap.get(key);
	}
	
	public void removeAttribute(String key) {
	  requestObjMap.remove(key);
	}
	
	public Enumeration<?> getAttributeNames(){
	  Vector<Object> vAttributeName = new Vector<Object>(requestObjMap.keySet());
	  System.out.println("vAttributeName "+vAttributeName);
	  Enumeration<?> enumeration = vAttributeName.elements(); 
	  return enumeration;
	}
	
	public String getProtocol(){
		return protocol;
	}
	
	public void setProtocol(String protocol){
	  this.protocol = protocol;	
	}


	public void setLocalAddress(String localAddress){
	  this.localAddress = localAddress;	
	}
	
	public String getLocalAddress(){
  	  return localAddress;
	}
	
	public void setRemoteAddress(String remoteAddress) {
	    this.remoteAddress = remoteAddress; 	
	}
	
	public String getRemoteAddr() {
		return remoteAddress;
	}
	
	public void setContentLength(final int contentLength) {
		this.contentLength = contentLength;
	}
	
	public int getContentLength() {
		return contentLength;
	}

	
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

    public Map<String, Object> getRequestMap(){
	return requestMap;
    }

    public void setRequestMap(Map<String, Object> requestMap) {
	this.requestMap = requestMap;
    }
	
    public void setParameterNames(Enumeration names){
	this.names = names;
    }

    public Enumeration<?> getParameterNames(){
       return names;
    }

    public String[] getParameterValues(String name){
	if(requestMap.get(name)== null) return null;
	String names = (String) requestMap.get(name);
	String [] values = names.split(",");
	return values;
    }

    public void setCharacterEncoding(String encoding){
		this.encoding = encoding;
    }
	
	public String getCharacterEncoding(){
		return encoding;
	}
	
	public void setLocalName(String localName) {
		this.localName = localName;
	}

	public String getLocalName() {
		return localName;
	}

	public void setLocalPort(String port) {
           this.port = port;		
	}
	
	
	public int getLocalPort() {
		int iPort =0;
		try{
			iPort = Integer.parseInt(port);
		}catch(Exception e) {
		}
		return iPort;
	}
	
	public void setBufferedReader(final BufferedReader reader) {
		this.reader = reader;
	}

	public BufferedReader getReader(){
		return reader;
	}

	public void setDateHeader(long dateHeader) {
		this.dateHeader = dateHeader;
	}

	public OutputStream getOutputStream() {
		return outputStream;
	}

   public void setOutputStream(OutputStream outputStream) {
 	this.outputStream = outputStream;
   }

    /**
     * @return the method
     */
    public String getMethod(){
        return method;
    }

    /**
     * @param method the method to set
     */
    public void setMethod(String method){
        this.method = method;
    }

    /**
     * @return the requestURI
     */
    public String getRequestURI(){
      return requestURI;
    }

    /**
     * @param requestURI the requestURI to set
     */
    public void setRequestURI(String requestURI){
     this.requestURI = requestURI;
    }

    /**
     * @return the pathInfo
     */
    public String getPathInfo() {
        return pathInfo;
    }

    /**
     * @param pathInfo the pathInfo to set
     */
    public void setPathInfo(String pathInfo) {
        this.pathInfo = pathInfo;
    }

    /**
     * @return the headerNames
     */
    public Enumeration getHeaderNames() {
        return headerNames;
    }

    /**
     * @param headerNames the headerNames to set
     */
    public void setHeaderNames(Enumeration headerNames) {
        this.headerNames = headerNames;
    }

    /**
     * @return the cookies
     */
    public Cookie[] getCookies() {
        return cookies;
    }
    
    public String getHeader(String header){
      return headerMap.get(header);
    }
    
    public void setHeaderMap(Map<String,String> headerMap){
       this.headerMap = headerMap;        
    }
 }

