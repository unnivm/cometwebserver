/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javax.servlet;

 import java.io.BufferedReader;
 import java.util.Enumeration;
 import java.util.Locale;
 import java.util.Map;

 public interface ServletRequest {
  
	void addAsyncListener(AsyncListener listener);
	void addAsyncListener(AsyncListener listener, ServletRequest servletRequest, ServletResponse servletResponse);
	Object getAttribute(java.lang.String name);
	public Enumeration<?> getAttributeNames();
	String getCharacterEncoding();
	public void setCharacterEncoding(String encoding);
	int getContentLength();
	String getContentType();
	String getLocalAddr();
	Locale getLocale();
	Enumeration<?> getLocales();
	String getLocalName();
	int getLocalPort() ;
	String getParameter(java.lang.String name);
	Map<String,Object> getParameterMap();
	Enumeration<?> getParameterNames();
	String [] getParameterValues(java.lang.String name);
	String 	getProtocol();
	BufferedReader getReader();
	String getRemoteAddr();
	void setAttribute(java.lang.String name, java.lang.Object obj);
	void removeAttribute(java.lang.String name);
	RequestDispatcher getRequestDispatcher(java.lang.String path);
 }
