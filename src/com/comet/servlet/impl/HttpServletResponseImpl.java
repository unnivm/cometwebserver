/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comet.servlet.impl;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

 public  class HttpServletResponseImpl implements HttpServletResponse {

  public StringBuffer sb = new StringBuffer();
	
  CometPrintWriter myPrintWriter = null;

  private Response response ;
	
  private boolean include = false;
	
  private String contentType;
        
  private List<Cookie> cookieList       = new ArrayList<Cookie>();
  
  private Map<String,Integer> intHeader = new HashMap<String, Integer>();
  
  private Map<String,Long> dateHeader   = new HashMap<String, Long>();
  
  private Map<String,String> header     = new HashMap<String, String>();
  
  public HttpServletResponseImpl (Response response){
    this.response = response;
  }
	
  @Override
  public void flushBuffer() throws IOException{
  }

  @Override
  public int getBufferSize(){
    return 0;
  }

   @Override
   public String getCharacterEncoding(){
    return null;
   }

   @Override
   public String getContentType(){
    return this.contentType;  
   }

   @Override
   public Locale getLocale(){
    return null;
   }

   @Override
   public PrintWriter getWriter(){
    try {
	myPrintWriter = new CometPrintWriter(new File("abc.txt"));
	if(response==null){
	   System.out.println(" response is coming NULL.....");
	}
	} catch (IOException e){
	}
    return myPrintWriter;
   }

 @Override
 public boolean isCommitted(){
  return false;
}

 @Override
 public void reset(){
 }

   @Override
   public void sendData(String str){
    sb.append(str);
    if(myPrintWriter !=null)
     myPrintWriter.setData(str);
   }

   @Override
   public String getData(){
   if(myPrintWriter != null)
   return myPrintWriter.getData();
   return "";
   }

   @Override
   public void addCookie(Cookie cookie){
     cookieList.add(cookie);
   }

   @Override
   public void sendRedirect(String location) throws IOException{
            
   }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
	  return (ServletOutputStream)response.getOutputStream();
    }

	public boolean isInclude() {
	  return include;
	}

	public void setInclude(boolean include) {
     	  this.include = include;
	}
	
        public void setContentType(String contentType) {
          this.contentType = contentType;  
        }

    @Override
    public void addDateHeader(String name, long date){
      dateHeader.put(name, date);
    }

    @Override
    public void addHeader(String name, String value){
      header.put(name, value);
    }

    @Override
    public void addIntHeader(String name, int value){
      intHeader.put(name, value); 
    }
    
    public List<Cookie> getCookiesList(){
       return cookieList; 
    }

    @Override
    public void setHeader(String name, String value){
      header.put(name,value);
    }
   
    public Map<String, String> getHeaderMap(){
        return header;
    }
    
 }

