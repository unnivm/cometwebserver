
package com.comet.server.http;

import com.comet.servlet.impl.HttpServletResponseImpl;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.Cookie;

/**
 *
 * @author Unni Vemanchery Mana
 */
public class HttpHeader{
    
    private CharSequence protocol = "HTTP/1.1";
    
    private CharSequence content;
    
    private CharSequence contentType;
    
    private String cookie;
    
    private int contentLength;
    
    private String prepareHttpStatus(int code){
        String s = "";
        switch(code){
            
            case 200:
            s +=" 200 OK";
            break;    
                
            case 400:
            s +=" 400 Bad Request";
            break;
                
            case 401:
            s +=" 401 Authorization Required";
            break;    
                
            case 403:
            s +=" 03 Forbidden";    
            break;
                
            case 404:
            s +=" 404 Not Found";
            break;
                
            case 500:
            s +=" 500 Internal Server Error";
            break;
                
            case 501:
            s +=" 501 Not Implemented";
            break;    
        }
        return s;
    }
    
 public String constructHttpResponse(int code){
  String s = "";
      s+=protocol+prepareHttpStatus(code)+"\r\n"+"Server: "+getServer()
          + "Content-length: " + contentLength + "\r\n"
          + "Content-type: "+ contentType + "\r\n\r\n"
          +((cookie == null)?"":"Set-Cookie:"+ cookie + "\r\n");
  return s;
 }
 
 public String constructImageHttpResponse(int code){
       StringBuilder sb = new StringBuilder();
       sb.append(protocol).append(prepareHttpStatus(code)).append("\r\n")
         .append("Server: ")
         .append(getServer()).append("ETag: ").append(" W/\"1231-1326774226001\"").append("\r\n")
         .append("Date: ").append(new Date().toGMTString()).append("\r\n")      
         .append("Content-length: ").append(contentLength).append("\r\n")
         .append("Last-Modified: ").append("Tue, 17 Jan 2012 04:23:46 GMT").append("\r\n")
         .append("Accept-Ranges: bytes").append("\r\n")      
         .append("Content-type: ").append(contentType).append("\r\n\r\n");
  return sb.toString();
 }
 
 public String constructHttpResponse(int code, HttpServletResponseImpl response) {
  System.out.println(" [inside constructHttpResponse() method] ");   
  String s = "";
  s+=protocol+prepareHttpStatus(code)+"\r\n"+"Server: "+getServer()
          + "Content-length: " + contentLength + "\r\n";
 List<Cookie> cookies = response.getCookiesList();
  Map<String, String> header = response.getHeaderMap();
  // processing response headers if any
  if( header != null && header.size()>0 ){
      Iterator<String> iterator = header.keySet().iterator();
      StringBuilder headerBuilder = new StringBuilder();
      while(iterator.hasNext()){
       String key = iterator.next();
       headerBuilder.append(key).append(": ").append(header.get(key)).append("\r\n");
      }
      s+=headerBuilder.toString();
  }
  // processing cookies if any
  System.out.println(" cookies list " + cookies);
  if(cookies != null && cookies.size()>0){
       StringBuilder cookieBuffer = new StringBuilder();
       for(Cookie c: cookies){
           cookieBuffer.append("Set-Cookie: ").append(c.getName()).append("=").append(c.getValue())
           .append((c.getMaxAge()>0)?";Expires="+new Date(c.getMaxAge()).toString():"")
           .append(";").append(c.getPath()!=null?";Path="+c.getPath():"").append(c.getDomain()!=null?";Domain="+c.getDomain():"")
           .append("\r\n");        
       }
      s+=cookieBuffer.toString();
  }
  s+="Content-type: "+ contentType + "\r\n\r\n";
  System.out.println(" ***** final s *** " + s);
  return s;
 }
 
    /**
     * @return the server
     */
    public String getServer(){
      return "Comet 1.0\r\n";
    }

    /**
     * @return the contentLength
     */
    public CharSequence getContent() {
        return content;
    }

    /**
     * @param contentLength the contentLength to set
     */
    public void setContent(CharSequence content) {
        this.content = content;
        if(this.content != null) contentLength = content.length();
    }

  
    /**
     * @return the contentType
     */
    public CharSequence getContentType() {
        return contentType;
    }

    /**
     * @param contentType the contentType to set
     */
    public void setContentType(CharSequence contentType) {
        this.contentType = contentType;
    }

    /**
     * @return the cookie
     */
    public String getCookie() {
        return cookie;
    }

    /**
     * @param cookie the cookie to set
     */
    public void setCookie(String cookie) {
        this.cookie = cookie;
    }
    
    public void setContentLength(int length){
        this.contentLength = length;
    }
    
    public static void main(String []arg){
        HttpHeader hh = new HttpHeader();
        hh.setContent("<br>Hello</br>");
        hh.setContentType("text/html");
        hh.setCookie(null);
        System.out.println(hh.constructHttpResponse(200));
    }
}
