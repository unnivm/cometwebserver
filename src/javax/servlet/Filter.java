/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javax.servlet;

/**
 *
 * @author unni_vm
 */
public interface Filter {
    
   void init(FilterConfig filterConfig) throws ServletException;
   
   void doFilter(ServletRequest request,ServletResponse response,FilterChain chain)throws java.io.IOException,ServletException;
   
   void destroy();
}
