/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javax.servlet;

 public interface ServletContext {

	 void addFilter(java.lang.String filterName, java.lang.String description, java.lang.String className, java.util.Map<java.lang.String,java.lang.String> initParameters, boolean isAsyncSupported);
     void addServlet(java.lang.String servletName, java.lang.String description, java.lang.String className, java.util.Map<java.lang.String,java.lang.String> initParameters, int loadOnStartup, boolean isAsyncSupported);
     void addServletMapping(java.lang.String servletName, java.lang.String[] urlPatterns);
     
 }
