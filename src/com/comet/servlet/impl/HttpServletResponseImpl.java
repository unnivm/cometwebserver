/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comet.servlet.impl;

import com.comet.server.http.Constants;
 import java.io.File;
 import java.io.FileNotFoundException;
 import java.io.IOException;
 import java.io.PrintWriter;
 import java.util.Locale;

import javax.servlet.ServletOutputStream;
 import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

 public  class HttpServletResponseImpl implements HttpServletResponse {

	public StringBuffer sb = new StringBuffer();
	
	CometPrintWriter myPrintWriter=null;

	private Response response ;
	
	private boolean include=false;
	
        private String contentType;
        
	public HttpServletResponseImpl (Response response) {
	  this.response = response;
	}
	
    @Override
	public void flushBuffer() throws IOException {
	}

    @Override
	public int getBufferSize() {
	   return 0;
	}

    @Override
	public String getCharacterEncoding() {
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
	public PrintWriter getWriter() {
		try {
			myPrintWriter = new CometPrintWriter(new File("abc.txt"));
			if(response==null){
			   System.out.println(" response is coming NULL.....");
			}
		} catch (IOException e) {
			e.printStackTrace();
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
	public void sendData(String str) {
		sb.append(str);
		if(myPrintWriter !=null)
			myPrintWriter.setData(str);
	}

        @Override
	public String getData(){
		if(myPrintWriter !=null)
	    return myPrintWriter.getData();
		return "";
	}

        @Override
	public void addCookie(Cookie cookie){
	}

        @Override
	public void sendRedirect(String location) throws IOException {
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
 }

