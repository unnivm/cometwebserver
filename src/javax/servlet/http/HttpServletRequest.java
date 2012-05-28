/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javax.servlet.http;

 import java.security.Principal;
import java.util.Enumeration;

import javax.servlet.ServletRequest;

 public interface HttpServletRequest extends ServletRequest{

	public HttpSession getSession();
	
	public HttpSession getSession(boolean flag);
	
	public String  getParameter(String name);
	
	public String getAuthType();
	
	public String getContextPath();
	
	public Cookie [] getCookies();
	
	public long getDateHeader(String name);
	
	public String getHeader(String name);
	
	public Enumeration getHeaderNames();
	
	public Enumeration getHeaders(String name);
	
	public int getIntHeader(String name);
	
	public String getMethod();
	
	public String getPathInfo();
	
	public String getPathTranslated();
	
	public String getQueryString();
	
	public String getRemoteUser();
	
	public String getRequestedSessionId();
	
	public String getRequestURI();
	
	public StringBuffer getRequestURL();
	
	public String getServletPath();
	
	public 	Principal getUserPrincipal();
	
	public boolean isRequestedSessionIdFromCookie();
	
	@Deprecated
	public boolean isRequestedSessionIdFromUrl();
	
	public boolean isRequestedSessionIdFromURL();
	
	public boolean isRequestedSessionIdValid();
	
	public boolean isUserInRole(java.lang.String role);
	
 }

