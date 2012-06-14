/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javax.servlet.http;

 import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;

 public interface HttpServletResponse extends ServletResponse {

	 public static final int  SC_ACCEPTED 				= 202;
	 
	 public static final int  SC_BAD_GATEWAY 			= 502;
	 
	 public static final int  SC_BAD_REQUEST 			= 400;
	 
	 public static final int  SC_CONFLICT 				= 409;
	 
	 public static final int SC_CONTINUE 				= 100;
	 
	 public static final int SC_CREATED 				= 201;
	 
	 public static final int SC_EXPECTATION_FAILED 		= 417;
	 
	 public static final int SC_INTERNAL_SERVER_ERROR 	= 417;
	 
	 public static final int SC_NOT_FOUND 				= 404;
	 
	 public void addCookie(Cookie cookie);

         public void addDateHeader(java.lang.String name, long date);
         
         public void addHeader(java.lang.String name, java.lang.String value);
         
         public void addIntHeader(java.lang.String name, int value);
         
	 public void sendRedirect(String location) throws java.io.IOException;
	 
         public void setHeader(String name, String value);
         
	 public ServletOutputStream getOutputStream() throws IOException;
 }

