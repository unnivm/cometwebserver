

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author unni_vm
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestParamExample extends HttpServlet {

   
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
        response.setContentType("text/html");
        
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Request Parameters Example</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h3>Request Parameters Example</h3>");
        out.println("<a href=\"../reqparams.html\">");
        out.println("<img src=\"../images/code.gif\" height=24 " +
                    "width=24 align=right border=0 alt=\"view code\"></a>");
        out.println("<a href=\"../index.html\">");
        out.println("<img src=\"../images/return.gif\" height=24 " +
                    "width=24 align=right border=0 alt=\"return\"></a>");
        out.println("Parameters in this request:<br>");
        out.println(request.getParameter("firstname"));
        out.println(request.getParameter("lastname"));
       // out.println(".... request getIntHeader ..." + request.getIntHeader("user-agent"));
        // itearting through enumeration
           Enumeration enums = request.getParameterNames();
           System.out.println(" enums.hasMoreElements().. " + enums.hasMoreElements());
           while(enums.hasMoreElements()){
             out.println((String)enums.nextElement()); 
           }
        // end of iterattion
        
        //if (firstName != null || lastName != null) {
        //    out.println("First Name:");
        //    out.println(" = " + HTMLFilter.filter(firstName) + "<br>");
        //    out.println("Last Name:");
        //    out.println(" = " + HTMLFilter.filter(lastName));
       // } else {
        out.println("No Parameters, Please enter some");
       // }
        out.println("<P>");
        out.print("<form action=\"");
        out.print("RequestParamExample\" ");
        out.println("method=POST>");
        out.println("First Name:");
        out.println("<input type=text size=20 name=firstname>");
        out.println("<br>");
        out.println("Last Name:");
        out.println("<input type=text size=20 name=lastname>");
        out.println("<br>");
        out.println("<input type=submit>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
        request.setAttribute("dispatcher", "/servlets/servlet/Cooker");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/servlets/servlet/Cooker");
        dispatcher.forward(request, response);
    }

    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse res)
    throws IOException, ServletException
    {
        doGet(request, res);
    }
}

