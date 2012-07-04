/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javax.servlet.http;

import java.io.IOException;
import javax.servlet.GenericServlet;
import javax.servlet.ServletException;

 public abstract class HttpServlet extends GenericServlet implements java.io.Serializable {

	 protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException,ServletException {
		 System.out.println("doGet() method  of HttpServlet was called.... ");
	 }
         
         protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException,ServletException {
             
         }
 }
