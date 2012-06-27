/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comet.servlet.impl;

import java.io.BufferedReader;
import java.nio.ByteBuffer;
import java.security.Principal;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncListener;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.comet.container.CometContainer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
 
 public class HttpServletRequestImpl implements HttpServletRequest {

    private Session    session;
 
    private Request    request;
 
    private ByteBuffer responseBuffer;
 
    private CometContainer cometContainer; 
	
    private static String BASIC_AUTH       = "BASIC";
        
    private static String CLIENT_CERT_AUTH = "CLIENT";
        
    private static String DIGEST_AUTH      = "DIGEST";
        
    private static String FORM_AUTH        = "FORM";

    private String dispatcherPath ;
    
 public HttpServletRequestImpl(Request request,ByteBuffer responseBuffer){
    	this.request = request;
    	this.responseBuffer = responseBuffer;
}
    
@Override
public HttpSession getSession(){
    return request.getSession();
}

 @Override
 public HttpSession getSession(boolean flag){
  /*
     if(flag) {
     session = new Session();
  }else {
      if(session == null){
        return null;	
      }else{
        return session;
      }
 }*/
     
 // return session;
  return request.getSession(flag);
 }
	
 @Override
 public String getParameter(String key){
   return request.getParameter(key);
 }
	
@Override
public Object getAttribute(String key){
   return request.getAttribute(key);
}

@Override
 public void addAsyncListener(AsyncListener listener){
 }

 @Override
 public void addAsyncListener(AsyncListener listener,
  ServletRequest servletRequest, ServletResponse servletResponse){
 }

@Override
public Enumeration<?> getAttributeNames() {
   return request.getAttributeNames();
}

	
@Override
public String getCharacterEncoding() {
  return request.getCharacterEncoding();
}
	
@Override
public int getContentLength(){
  return request.getContentLength();
}

@Override
public String getContentType(){
 return request.getContentType();
}

@Override
public String getLocalAddr(){
  return request.getLocalAddress();
}

@Override
public String getLocalName(){
 return request.getLocalName();
}

@Override
public int getLocalPort(){
  return request.getLocalPort();
}

 @Override
 public Locale getLocale(){
  return null;
 }

@Override
public Enumeration<?> getLocales(){
  return null;
}
	
@Override
public Enumeration<?> getParameterNames(){
  return request.getParameterNames();
}

@Override
public String[] getParameterValues(String name){
return request.getParameterValues(name);
}

 @Override
 public String getProtocol(){
   String protocol = request.getProtocol();
   return protocol;
}

 @Override
 public BufferedReader getReader(){
   return request.getReader();
 }

@Override
public String getRemoteAddr(){
  return request.getRemoteAddr();
}

 @Override
 public void removeAttribute(String name){
  request.removeAttribute(name);
 }

@Override
public void setAttribute(String name, Object obj){
  request.setAttribute(name, obj);
}

@Override
public Map<String, Object> getParameterMap(){
  return request.getParameterMap();
}

@Override
public void setCharacterEncoding(String encoding){
  request.setCharacterEncoding(encoding);
}

@Override
public RequestDispatcher getRequestDispatcher(String path){
  path = dispatcherPath;  
  return new RequestDispatcherImpl(path);
}

public CometContainer getCometContainer(){
 return cometContainer;
}

public void setCometContainer(CometContainer cometContainer){
  this.cometContainer = cometContainer;
}

public Request getRequest(){
  return request;
}

public void setRequest(Request request){
  this.request = request;
}

@Override
public long getDateHeader(String name){
 Date date = null;
 String value = request.getHeader(name);
 java.text.SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 java.util.Calendar cal = Calendar.getInstance(new SimpleTimeZone(0, "GMT"));
 format.setCalendar(cal);
 try {
       date = format.parse(value);
     } catch (ParseException ex){
        Logger.getLogger(HttpServletRequestImpl.class.getName()).log(Level.SEVERE, null, ex);
        throw new IllegalArgumentException(" date is invalid ");
       }
 return date.getTime();	
}

@Override
public String getAuthType(){
 return request.getAuthType();
}

 @Override
 public String getContextPath(){
  return request.getContextPath();
 }

 @Override
 public Cookie[] getCookies(){
   return request.getCookies();
 }

 @Override
 public String getHeader(String name){
   return request.getHeader(name);
}

@Override
public Enumeration getHeaderNames(){
  return request.getHeaderNames();
}

@Override
public Enumeration getHeaders(String name){
  return request.getHeaders(name);
}

@Override
public int getIntHeader(String name){
  int value = Integer.parseInt(request.getHeader(name));
  return value;
}
        
@Override
public String getMethod(){
 return request.getMethod();
}

@Override
public String getPathInfo(){
  return request.getPathInfo();
}

@Override
public String getPathTranslated(){
  return request.getPathTranslated();
}

@Override
public String getQueryString(){
  return request.getQueryString();
}

@Override
public String getRemoteUser(){
 return null;
}

@Override
public String getRequestURI(){
 return request.getRequestURI();
}

@Override
public StringBuffer getRequestURL(){
  return request.getRequestURL();
}

@Override
public String getRequestedSessionId(){
  return null;
}

 @Override
 public String getServletPath(){
  return request.getServletPath();
 }

@Override
public Principal getUserPrincipal(){
  return null;
}

@Override
public boolean isRequestedSessionIdFromCookie(){
  return false;
}

@Override
public boolean isRequestedSessionIdFromURL(){
 return false;
}

@Override
public boolean isRequestedSessionIdFromUrl(){
  return false;
}

@Override
public boolean isRequestedSessionIdValid(){
  return false;
}

@Override
public boolean isUserInRole(String role){
   return false;
}

}
