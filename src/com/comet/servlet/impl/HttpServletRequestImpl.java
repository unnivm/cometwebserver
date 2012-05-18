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
import javax.servlet.http.Session;

import com.comet.container.CometContainer;
 
 public class HttpServletRequestImpl implements HttpServletRequest {

	private Session    session;
	private Request    request;
        private ByteBuffer responseBuffer;
	private CometContainer cometContainer; 
	
public HttpServletRequestImpl(Request request,ByteBuffer responseBuffer) {
    	this.request = request;
    	this.responseBuffer = responseBuffer;
}
    
@Override
public HttpSession getSession() {
	    if(session==null)
		session = new Session();
		return session;
}

@Override
public HttpSession getSession(boolean flag){
		if(flag) {
			session = new Session();
		}else {
			if(session==null) {
			  return null;	
			}else {
				return session;
			}
		}
 return session;
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
public void addAsyncListener(AsyncListener listener) {
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
public int getContentLength() {
  return request.getContentLength();
}

@Override
public String getContentType() {
 return request.getContentType();
}

@Override
public String getLocalAddr() {
  return request.getLocalAddress();
}

@Override
public String getLocalName() {
 return request.getLocalName();
}

@Override
public int getLocalPort() {
  return request.getLocalPort();
}

 @Override
 public Locale getLocale(){
  return null;
 }

@Override
public Enumeration<?> getLocales() {
  return null;
}
	
@Override
public Enumeration<?> getParameterNames() {
  return request.getParameterNames();
}

@Override
public String[] getParameterValues(String name) {
return request.getParameterValues(name);
}

 @Override
 public String getProtocol() {
   String protocol = request.getProtocol();
   return protocol;
}

 @Override
 public BufferedReader getReader(){
   return request.getReader();
 }

@Override
public String getRemoteAddr() {
  return request.getRemoteAddr();
}

 @Override
 public void removeAttribute(String name){
  request.removeAttribute(name);
 }

@Override
public void setAttribute(String name, Object obj) {
  request.setAttribute(name, obj);
}

@Override
public Map<String, Object> getParameterMap() {
  return request.getParameterMap();
}

@Override
public void setCharacterEncoding(String encoding){
  request.setCharacterEncoding(encoding);
}

@Override
public RequestDispatcher getRequestDispatcher(String path) {
  return new RequestDispatcherImpl(path);
}

public CometContainer getCometContainer() {
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
public long getDateHeader(String name) {
 return 0;	
}

@Override
public String getAuthType(){
 return null;
}

  @Override
  public String getContextPath(){
    return null;
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
  return null;
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
  return null;
}

@Override
public String getPathTranslated(){
  return null;
}

@Override
public String getQueryString(){
  return null;
}

@Override
public String getRemoteUser(){
 return null;
}

@Override
public String getRequestURI(){
  return null;
}

@Override
public StringBuffer getRequestURL(){
  return null;
}

@Override
public String getRequestedSessionId(){
  return null;
}

 @Override
 public String getServletPath(){
  return null;
 }

@Override
public Principal getUserPrincipal() {
  return null;
}

@Override
public boolean isRequestedSessionIdFromCookie() {
  return false;
}

@Override
public boolean isRequestedSessionIdFromURL() {
 return false;
}

@Override
public boolean isRequestedSessionIdFromUrl() {
  return false;
}

@Override
public boolean isRequestedSessionIdValid() {
  return false;
}

@Override
public boolean isUserInRole(String role) {
   return false;
 }

}
