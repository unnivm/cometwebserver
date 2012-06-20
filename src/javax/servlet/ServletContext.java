/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javax.servlet;

import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import javax.servlet.descriptor.JspConfigDescriptor;

 public interface ServletContext {
     
     FilterRegistration.Dynamic addFilter(java.lang.String filterName, java.lang.Class<? extends Filter> filterClass); 
     
     FilterRegistration.Dynamic addFilter(java.lang.String filterName, Filter filter);
     
     FilterRegistration.Dynamic addFilter(java.lang.String filterName, java.lang.String className);

     void addListener(java.lang.Class<? extends java.util.EventListener> listenerClass);
     
     void addListener(java.lang.String className);
     
     <T extends java.util.EventListener>void addListener(T t);
     
     ServletRegistration.Dynamic addServlet(java.lang.String servletName, java.lang.Class<? extends Servlet> servletClass); 
     
     ServletRegistration.Dynamic addServlet(java.lang.String servletName, Servlet servlet);
     
     ServletRegistration.Dynamic addServlet(java.lang.String servletName, java.lang.String className);

     <T extends Filter>T createFilter(java.lang.Class<T> c);
     
     <T extends java.util.EventListener>T createListener(java.lang.Class<T> c);
     
     <T extends Servlet>T createServlet(java.lang.Class<T> c);
     
     void declareRoles(java.lang.String... roleNames);
     
     Object getAttribute(java.lang.String name);
     
     Enumeration<java.lang.String> 	getAttributeNames();
     
     ClassLoader getClassLoader();
     
     ServletContext getContext(String uripath);
     
     String getContextPath();
     
     Set<SessionTrackingMode> getDefaultSessionTrackingModes();
     
     int getEffectiveMajorVersion();
     
     int getEffectiveMinorVersion();
     
     Set<SessionTrackingMode> 	getEffectiveSessionTrackingModes(); 
     
     FilterRegistration getFilterRegistration(java.lang.String filterName);
     
     Map<java.lang.String,? extends FilterRegistration> getFilterRegistrations();
     
     String getInitParameter(java.lang.String name);
     
     Enumeration<java.lang.String> getInitParameterNames();
     
     JspConfigDescriptor 	getJspConfigDescriptor();
     
     int getMajorVersion();
     
     String getMimeType(java.lang.String file);
     
     int getMinorVersion();
     
     RequestDispatcher 	getNamedDispatcher(java.lang.String name);
     
     String getRealPath(java.lang.String path);
     
     RequestDispatcher 	getRequestDispatcher(java.lang.String path);
     
     URL getResource(java.lang.String path);
     
     InputStream getResourceAsStream(java.lang.String path);
     
     Set<String> getResourcePaths(java.lang.String path);
     
     String getServerInfo();
     
     Servlet getServlet(java.lang.String name);
     
     String getServletContextName();
     
     Enumeration<java.lang.String> getServletNames();
     
     ServletRegistration getServletRegistration(String servletName);
     
     Map<String,? extends ServletRegistration> 	getServletRegistrations();
     
     Enumeration<Servlet> getServlets();
     
     SessionCookieConfig getSessionCookieConfig();
     
     void log(java.lang.Exception exception, String msg);
     
     void log(java.lang.String msg);
     
     void log(java.lang.String message, Throwable throwable);
     
     void removeAttribute(String name);
     
     void setAttribute(java.lang.String name, java.lang.Object object);
     
     boolean setInitParameter(java.lang.String name, java.lang.String value);
     
     void setSessionTrackingModes(java.util.Set<SessionTrackingMode> sessionTrackingModes);
     
 }
