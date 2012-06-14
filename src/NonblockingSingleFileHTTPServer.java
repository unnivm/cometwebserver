

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
  
  private final static String strDefaultFile = "c:/comet/src/index.html"; 

  private  HttpRequestManager httpRequestManager = new HttpRequestManager();
  
  private Request request = new Request(); 
 
  private Response response = new Response();
  
  private CometContainer container = null;
  
  private static final Logger logger = Logger.getLogger(NonblockingSingleFileHTTPServer.class.getName());
  
  private File file ;
  
  private ClassLoader cl;
  
  private URL url;
  
  private URL[] urls;
  
  private NonblockingSingleFileHTTPServer(ByteBuffer data, CharSequence encoding, CharSequence MIMEType, int port)
   throws UnsupportedEncodingException {
    this.port = port;
    System.out.println(" MIMEType " + MIMEType);
    initializeContainer();
  }
  
  
  private NonblockingSingleFileHTTPServer(int port){
      this.port = port;
      initializeContainer();
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
    logger.log(Level.INFO, " ...... Server Started......");
    while (true) {
      selector.select();
      Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
      while (keys.hasNext()) {
        SelectionKey key = (SelectionKey) keys.next();
        keys.remove();
        try {
          if (key.isAcceptable()) {
            logger.log(Level.INFO, ".....ACCEPTING.....");
            ServerSocketChannel server = (ServerSocketChannel) key.channel();
            SocketChannel channel = server.accept();
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ);
          }
          else if (key.isWritable()) {
            logger.log(Level.INFO, ".....WRITING.....");
            SocketChannel channel = (SocketChannel) key.channel();
            ByteBuffer buffer = (ByteBuffer) key.attachment();
            if (buffer!=null && buffer.hasRemaining()){
               channel.write(buffer);
            }
            else {  // we're done
               channel.close();
            }
          }
          else if (key.isReadable()) {
            logger.log(Level.INFO, ".....READING......");
            SocketChannel channel = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(4096);
            channel.read(buffer);
            long time = System.currentTimeMillis();
            processRequest(buffer,channel);
            long now = System.currentTimeMillis();
            System.out.println(" time taken:: " + ( now - time));
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
  
  private static ByteBuffer getDefaultContent() throws IOException {
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
        int port = 8700;
        CharSequence encoding = "ISO-8859-1";
        if (args !=null && args.length > 2) encoding = args[2];
        NonblockingSingleFileHTTPServer server = new NonblockingSingleFileHTTPServer(port);
        server.run();
    }
    catch (Exception ex){
      System.err.println(ex);
      ex.printStackTrace();
    }
  }
  
 private boolean loadHttpServlet(){
   WebResource resource = httpRequestManager.getResource();
   String servlet = null;
   if(resource.processForValidResource(servlet)){
      servlet = resource.getResourceValue();
      String path = resource.getResourcePath();
      logger.log(Level.INFO, " what is the loading servlet .... {0}", servlet);
      try{
            if( file == null ){
               file = new File(path);
               url = file.toURI().toURL();
               urls = new URL[]{url};
            }
            if(!path.equals(file.getAbsolutePath())){
              file = new File(path);
              url = file.toURI().toURL();
              urls = new URL[]{url};
            }
            cl = new URLClassLoader(urls);
            Object object2 = cl.loadClass(servlet).newInstance();
	    Method [] methods = object2.getClass().getDeclaredMethods();
	    httpServletResponseImpl = new HttpServletResponseImpl(response);
	    httpServletRequestImpl  = new HttpServletRequestImpl(request,contentBuffer);
	    Object [] arguement = new Object[2];
	    arguement[0] = httpServletRequestImpl;
	    arguement[1] = httpServletResponseImpl;
	    String httpMethod = resource.getMethod();
            Object object = "GET".equals(httpMethod)? methods[0].invoke(object2, arguement): methods[1].invoke(object2, arguement);
	}catch(Exception e){
          logger.log(Level.SEVERE,e.toString());
	}
        return true;
       } 
       return false;
 }
  
  private static String getDefaultFIleContent(){
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
     CharSequence strContent    = null;
     WebResource resource = httpRequestManager.getResource();
     StringBuilder sb     = new StringBuilder();
     HttpHeader hh = new HttpHeader();
     if(loadHttpServlet()){
     strContent = httpServletResponseImpl.getData();
     hh.setContent(strContent);
     hh.setContentType(httpServletResponseImpl.getContentType()==null ? Constants.html: httpServletResponseImpl.getContentType());
     sb.append(hh.constructHttpResponse(200, httpServletResponseImpl));
     sb.append(strContent);
     setContentBuffer(sb);
     }else{
        if(resource.getResource().endsWith(".htm") || resource.getResource().endsWith(".html")){
           strContent = FileUtil.readFile(resource.getContext() + "/" + resource.getResource());
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
   httpRequestManager.processHeaderDataFromRequest1(buffer,request,response,channel);
   if(httpRequestManager.isContextPath()){
       processContentWithContext();
       return;
   }
   contentBuffer = getDefaultContent();
 }
 
 private void setContentBuffer(StringBuilder sb){
   ByteBuffer buffer = ByteBuffer.allocate(sb.toString().length());
   byte[] headerData = null;
   try{
       headerData = sb.toString().getBytes("ASCII");
   }catch (UnsupportedEncodingException ex) {
       logger.log(Level.SEVERE, null, ex);
   }
    buffer.put(headerData);
    buffer.flip();
    if(this.contentBuffer != null)
    this.contentBuffer.clear();
    this.contentBuffer = buffer;
 }
 
 private void setContentByteBuffer(byte [] b){
     if(b == null) return;
     HttpHeader hh = new HttpHeader();
     hh.setContentLength(b.length);
     hh.setContentType(Constants.image);
     StringBuilder sb = new StringBuilder();
     sb.append(hh.constructImageHttpResponse(200));
     byte [] headerData = null;
        try {
          headerData = sb.toString().getBytes("ASCII");
        } catch (UnsupportedEncodingException ex){
          logger.log(Level.SEVERE, null, ex);
        }
     // constructing a single binary array
     byte [] image = new byte[b.length + headerData.length];
     System.arraycopy(headerData, 0, image, 0, headerData.length);
     System.arraycopy(b, 0, image, headerData.length, b.length);
     ByteBuffer buffer = ByteBuffer.allocate(image.length); //
     buffer.put(image);
     buffer.flip();
     if(this.contentBuffer != null)
     this.contentBuffer.clear();
     this.contentBuffer = buffer;
 }
 
}

