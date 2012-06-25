/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javax.servlet;

import java.io.IOException;

 public interface RequestDispatcher {
	 
	void forward(ServletRequest request, ServletResponse response) throws IOException;
	void include(ServletRequest request, ServletResponse response)throws IOException;
	
 }
