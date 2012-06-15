/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javax.servlet;

 import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

 public interface ServletResponse {
	 
	 public void flushBuffer() throws java.io.IOException ;
	 
	 public int getBufferSize();
	 
	 public String getCharacterEncoding();
	 
	 public String getContentType();
	 
	 public Locale getLocale();
	 
	 public PrintWriter getWriter() ;
	 
	 public boolean isCommitted();
	 
	 public void reset();
	 
	 public void sendData(String str);
	 
	 public String getData();
	 
	 public ServletOutputStream getOutputStream() throws IOException; 
		 
	
 }
