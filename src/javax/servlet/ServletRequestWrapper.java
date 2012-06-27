/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javax.servlet;

import java.io.BufferedReader;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author unni_vm
 */
public class ServletRequestWrapper implements ServletRequest {

    private ServletRequest request;
    
    public ServletRequestWrapper(ServletRequest request){
      if(request == null) 
          throw new IllegalArgumentException("Request cannot be null");   
      this.request = request;
    }
    
    @Override
    public void addAsyncListener(AsyncListener listener) {
    }

    @Override
    public void addAsyncListener(AsyncListener listener, ServletRequest servletRequest, ServletResponse servletResponse) {
    }

    @Override
    public Object getAttribute(String name) {
        return request.getAttribute(name);
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
    public void setCharacterEncoding(String encoding) {
        this.request.setCharacterEncoding(encoding);
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
      return request.getLocalAddr();
    }

    @Override
    public Locale getLocale() {
        return request.getLocale();
    }

    @Override
    public Enumeration<?> getLocales() {
        return request.getLocales();
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
    public String getParameter(String name) {
        return request.getParameter(name);
    }

    @Override
    public Map<String, Object> getParameterMap() {
        return request.getParameterMap();
    }

    @Override
    public Enumeration<?> getParameterNames(){
      return request.getParameterNames();
    }

    @Override
    public String[] getParameterValues(String name) {
        return request.getParameterValues(name);
    }

    @Override
    public String getProtocol() {
        return request.getProtocol();
    }

    @Override
    public BufferedReader getReader() {
        return request.getReader();
    }

    @Override
    public String getRemoteAddr() {
        return request.getRemoteAddr();
    }

    @Override
    public void setAttribute(String name, Object obj) {
        this.request.setAttribute(name, obj);
    }

    @Override
    public void removeAttribute(String name) {
       this.request.removeAttribute(name);
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String path) {
        return request.getRequestDispatcher(path);
    }
    
}
