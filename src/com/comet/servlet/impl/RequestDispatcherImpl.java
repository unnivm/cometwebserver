/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comet.servlet.impl;

import com.comet.container.CometContainer;
import com.comet.container.ContainerContext;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

 public class RequestDispatcherImpl implements RequestDispatcher{

    static java.lang.String 	ERROR_EXCEPTION ;
    
    static java.lang.String 	ERROR_EXCEPTION_TYPE ;

    static java.lang.String 	ERROR_MESSAGE;
    
    static java.lang.String 	ERROR_REQUEST_URI;
    
    static java.lang.String 	ERROR_SERVLET_NAME;
    
    static java.lang.String 	ERROR_STATUS_CODE;
    
    static java.lang.String 	FORWARD_CONTEXT_PATH;
    
    static java.lang.String 	FORWARD_PATH_INFO;
    
    static java.lang.String 	FORWARD_QUERY_STRING;
    
    static java.lang.String 	FORWARD_REQUEST_URI;
    
    static java.lang.String 	FORWARD_SERVLET_PATH;
    
    static java.lang.String 	INCLUDE_CONTEXT_PATH;
    
    static java.lang.String 	INCLUDE_PATH_INFO;
    
    static java.lang.String 	INCLUDE_QUERY_STRING;
    
    static java.lang.String 	INCLUDE_REQUEST_URI;
    
    static java.lang.String 	INCLUDE_SERVLET_PATH;
    
    private String path;
	
 public RequestDispatcherImpl(String path) {
     System.out.println("...inside RequestDispatcherImpl() ");
    this.path = path;
 }
	
	
@Override
public void forward(ServletRequest request, ServletResponse response) throws IOException{

    /*
    try {
			CometContainer cometContainer = new CometContainer();
			cometContainer.setHttpServletRequestImpl((HttpServletRequestImpl)request);
			cometContainer.setHttpServletResponseImpl((HttpServletResponseImpl)response);
			cometContainer.loadHttpServlet(path);
	}catch (Exception e){
            
	}
        * 
        */
    System.out.println("...inside forward().....");
    CometContainer container = ContainerContext.getContainerContext().getContainer();
    System.out.println(" container " + container);
    if(container == null) return;
    System.out.println(" AAAAAA " + container);
    container.setHttpServletRequestImpl((HttpServletRequestImpl)request);
    container.setHttpServletResponseImpl((HttpServletResponseImpl)response);
    System.out.println(" BBBBBBBB " + container);
    container.loadHttpServlet(path);
 }

 @Override
 public void include(ServletRequest request, ServletResponse response) {
		try {
			CometContainer cometContainer = new CometContainer();
			HttpServletResponseImpl httpServletResponseImpl = (HttpServletResponseImpl) response;
			httpServletResponseImpl.setInclude(true);
			cometContainer.setHttpServletRequestImpl((HttpServletRequestImpl)   request);
			cometContainer.setHttpServletResponseImpl(httpServletResponseImpl);
			Object result = cometContainer.loadIncludedURI(path);
			// setting the result to the response
			//((HttpServletResponseImpl) response).sendData(str)
		} catch (Exception e) {
			e.printStackTrace();
		}
 }
 
 }
