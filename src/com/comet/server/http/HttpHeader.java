
package com.comet.server.http;

/**
 *
 * @author Unni Vemanchery Mana
 */
public class HttpHeader{
    
    private String protocol = "HTTP/1.1";
    
    private String content;
    
    private String contentType;
    
    private String cookie;
    
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
      String s="";
      s+=protocol+prepareHttpStatus(code)+"\r\n"+"Server: "+getServer()
          +((cookie == null)?"":"Set-Cookie:"+ cookie + "\r\n")
          + "Content-length: " + content.length() + "\r\n"
          + "Content-type: "+ contentType + "\r\n\r\n";
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
    public String getContent() {
        return content;
    }

    /**
     * @param contentLength the contentLength to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the contentType
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * @param contentType the contentType to set
     */
    public void setContentType(String contentType) {
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
    
    
    public static void main(String []arg){
        HttpHeader hh = new HttpHeader();
        hh.setContent("<br>Hello</br>");
        hh.setContentType("text/html");
        hh.setCookie(null);
        System.out.println(hh.constructHttpResponse(200));
    }
}
