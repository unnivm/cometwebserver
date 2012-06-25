/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comet.servlet.impl;

import com.comet.container.CometContainer;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

 public class RequestDispatcherImpl implements RequestDispatcher{

 private String path;
	
 public RequestDispatcherImpl(String path) {
		this.path = path;
 }
	
	
    @Override
 public void forward(ServletRequest request, ServletResponse response) throws IOException {
		try {
			CometContainer cometContainer = new CometContainer();
			cometContainer.setHttpServletRequestImpl((HttpServletRequestImpl)request);
			cometContainer.setHttpServletResponseImpl((HttpServletResponseImpl)response);
			cometContainer.loadHttpServlet(path);
		} catch (Exception e) {
		}
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
