

import com.comet.container.CometContainer;
import com.comet.server.http.Constants;
import com.comet.server.http.HttpHeader;
import com.comet.server.http.HttpRequestManager;
import com.comet.server.http.WebResource;
import com.comet.servlet.impl.HttpServletRequestImpl;
import com.comet.servlet.impl.HttpServletResponseImpl;
import com.comet.servlet.impl.Request;
import com.comet.servlet.impl.Response;
import com.comet.utils.FileUtil;
import com.comet.utils.StringUtils;
import java.io.*;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

 public class NonblockingSingleFileHTTPServer{

  private ByteBuffer contentBuffer;
  
  private int port = 8700;
     
  private HttpServletResponseImpl httpServletResponseImpl;
  
  private HttpServletRequestImpl httpServletRequestImpl;	
  
  private final String strDefaultFile = "c:/comet/src/index.html"; 

  private  HttpRequestManager httpRequestManager = new HttpRequestManager();
  
  private Request request = new Request(); 
 
  private Response response = new Response();
  
  private CometContainer container = null;
  
  private NonblockingSingleFileHTTPServer(ByteBuffer data, String encoding, String MIMEType, int port)
   throws UnsupportedEncodingException {
    this.port = port;
    System.out.println(" MIMEType " + MIMEType);
    String header = "HTTP/1.0 200 OK\r\n"
     + "Server: Comet 1.0\r\n"
     + "Content-length: " + data.limit() + "\r\n"
     + "Content-type: text/html\r\n\r\n";
     byte[] headerData = header.getBytes("ASCII");
     ByteBuffer buffer = ByteBuffer.allocate(
     data.limit() + headerData.length);
     buffer.put(headerData);
     buffer.put(data);
     buffer.flip();
     if(this.contentBuffer !=null) this.contentBuffer.clear();
     this.contentBuffer = buffer;
     initializeContainer();
  }
  
  private NonblockingSingleFileHTTPServer(){
  }
  
  
  private void initializeContainer(){
    container = new CometContainer(httpRequestManager);
    container.init();
  }
  
  private void run() throws IOException {
    ServerSocketChannel serverChannel = ServerSocketChannel.open();
    ServerSocket  serverSocket = serverChannel.socket();
    Selector selector = Selector.open();
    InetSocketAddress localPort = new InetSocketAddress(port);
    serverSocket.bind(localPort);
    serverChannel.configureBlocking(false);
    serverChannel.register(selector, SelectionKey.OP_ACCEPT);
    System.out.println(" ...... Server Started......");
    while (true) {
      selector.select();
      Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
      while (keys.hasNext()) {
        SelectionKey key = (SelectionKey) keys.next();
        keys.remove();
        try {
          if (key.isAcceptable()) {
            System.out.println(".....ACCEPTING.....");
            ServerSocketChannel server = (ServerSocketChannel) key.channel();
            SocketChannel channel = server.accept();
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ);
          }
          else if (key.isWritable()) {
            System.out.println(".....WRITING.....");
            SocketChannel channel = (SocketChannel) key.channel();
            ByteBuffer buffer = (ByteBuffer) key.attachment();
            if (buffer!=null && buffer.hasRemaining()) {
               channel.write(buffer);
            }
            else {  // we're done
               channel.close();
            }
          }
          else if (key.isReadable()) {
            System.out.println(".....READING......");
            SocketChannel channel = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(4096);
            channel.read(buffer);
            processRequest(buffer,channel);
            
            // switch channel to write-only mode
            key.interestOps(SelectionKey.OP_WRITE);
            key.attach(contentBuffer.duplicate());
          }
        }
        catch (IOException ex) {
          key.cancel();
          try {
            key.channel().close();
          }
          catch (IOException cex) {}
        }
      }
    }
  }
  
  private ByteBuffer getDefaultContent() throws IOException {
      String strContent = null;
      strContent  = getDefaultFIleContent();
      ByteBuffer buffer   = ByteBuffer.allocate(strContent.length());
      byte[] headerData   = strContent.getBytes("ASCII");
      buffer.put(headerData);
      buffer.flip();
      return buffer;
  }
  
  public static void main(String[] args){
    try {
    	String contentType = "text/html";
        int port = 8700;
        String encoding = "ISO-8859-1";
        if (args !=null && args.length > 2) encoding = args[2];
        NonblockingSingleFileHTTPServer server = new NonblockingSingleFileHTTPServer(new NonblockingSingleFileHTTPServer().getDefaultContent(), encoding, contentType, port);
        server.run();
    }
    catch (Exception ex){
      System.err.println(ex);
    }
  }
  
 private boolean loadHttpServlet() {
   WebResource resource = httpRequestManager.getResource();
   String servlet = null;
   if(resource.processForValidResource(servlet)){
      servlet = resource.getResourceValue();
      String path = resource.getResourcePath();
      System.out.println(" what is the loading servlet .... " + servlet);
      System.out.println(" what is the loading servlet path.... " + path);
      try{
            File file = new File(path);
            URL url = file.toURI().toURL();
            URL [] urls = new URL[]{url};
            ClassLoader cl = new URLClassLoader(urls);
            Object object2 = cl.loadClass(servlet).newInstance();
	    Method [] methods = object2.getClass().getDeclaredMethods();
	    httpServletResponseImpl = new HttpServletResponseImpl(response);
	    httpServletRequestImpl = new HttpServletRequestImpl(request,contentBuffer);
	    Object [] arguement = new Object[2];
	    arguement[0] = httpServletRequestImpl;
	    arguement[1] = httpServletResponseImpl;
	    String httpMethod = resource.getMethod();
            Object object = "GET".equals(httpMethod)? methods[0].invoke(object2, arguement): methods[1].invoke(object2, arguement);
	}catch(Exception e){
          e.printStackTrace();
          return false;
	}
        return true;
       } 
       return false;
 }
  
  private String getDefaultFIleContent(){
    String str = null;
    FileInputStream fin = null;
    try {
    	  fin = new FileInputStream(strDefaultFile);
    	  str = StringUtils.convertStreamToString(fin);
	} catch (FileNotFoundException e){
	}
    return str;
  }
  
  private  void processContentWithContext() throws IOException{
     String strContent    = null;
     WebResource resource = httpRequestManager.getResource();
     StringBuilder sb     = new StringBuilder();
     HttpHeader hh = new HttpHeader();
     if(loadHttpServlet()){
     strContent = httpServletResponseImpl.getData();
     hh.setContent(strContent);
     hh.setContentType(httpServletResponseImpl.getContentType()==null ? Constants.html: httpServletResponseImpl.getContentType());
     sb.append(hh.constructHttpResponse(200));
     sb.append(strContent);
     setContentBuffer(sb);
     }else{
        if(resource.getResource().endsWith(".htm") || resource.getResource().endsWith(".html")){
           strContent = FileUtil.readFile(resource.getContext()+"/"+resource.getResource());
           hh.setContent(strContent);
           hh.setContentType(Constants.html);
           sb.append(hh.constructHttpResponse(200));
           sb.append(strContent);
           setContentBuffer(sb);
        }
        else if(resource.getResource().endsWith(".png") || resource.getResource().endsWith(".gif") || resource.getResource().endsWith(".jpg")){
           byte [] b = FileUtil.loadResource(resource.getContext()+"/"+resource.getResource());
           setContentByteBuffer(b);
        }
        else {
            System.out.println(".... It is not a valid resource....");
            String error = "<html><head><title>Comet -1.0 - Error report</title><style><!--H1 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:22px;} H2 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:16px;} H3 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:14px;} BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;} B {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;} P {font-family:Tahoma,Arial,sans-serif;background:white;color:black;font-size:12px;}A {color : black;}A.name {color : black;}HR {color : #525D76;}--></style> </head><body><h1>HTTP Status 404 - /unni/servlets/servlet/HelloWorldExample</h1><HR size=\"1\" noshade=\"noshade\"><p><b>type</b> Status report</p><p><b>message</b> <u>/unni/servlets/servlet/HelloWorldExample</u></p><p><b>description</b> <u>The requested resource (/unni/servlets/servlet/HelloWorldExample) is not available.</u></p><HR size=\"1\" noshade=\"noshade\"><h3>Comet - 1.0</h3></body></html>";
             hh.setContent(error);
             hh.setContentType(Constants.html);
             sb.append(hh.constructHttpResponse(404));
             sb.append(error);
             setContentBuffer(sb);
        }
     }
 }

 public int getPort() {
	return port;
 }

 public void setPort(int port){
	this.port = port;
 }
  
 private void processRequest(ByteBuffer buffer,SocketChannel channel) throws IOException {
   httpRequestManager.processHeaderDataFromRequest(buffer,request,response,channel);
   if(httpRequestManager.isContextPath()) processContentWithContext();
   else
   contentBuffer = getDefaultContent();
 }
 
 private void setContentBuffer(StringBuilder sb){
   ByteBuffer buffer = ByteBuffer.allocate(sb.toString().length());
   byte[] headerData = null;
   try{
       headerData = sb.toString().getBytes("ASCII");
   }catch (UnsupportedEncodingException ex) {
       Logger.getLogger(NonblockingSingleFileHTTPServer.class.getName()).log(Level.SEVERE, null, ex);
   }
    buffer.put(headerData);
    buffer.flip();
    this.contentBuffer.clear();
    this.contentBuffer = buffer;
 }
 
 private void setContentByteBuffer(byte [] b){
     if(b == null) return;
     ByteBuffer buffer = ByteBuffer.allocate(b.length);
     buffer.put(b);
     buffer.flip();
     this.contentBuffer.clear();
     this.contentBuffer = buffer;
 }
 
}

