/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javax.servlet.http;

 import java.io.IOException;

 import javax.servlet.GenericServlet;

 public abstract class HttpServlet extends GenericServlet implements java.io.Serializable {

	 protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		 System.out.println("doGet() method  of HttpServlet was called.... ");
	 }
 }
