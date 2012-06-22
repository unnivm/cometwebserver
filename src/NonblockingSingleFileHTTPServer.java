
import com.comet.container.CometContainer;
import com.comet.core.CometState;
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
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NonblockingSingleFileHTTPServer {

    private ByteBuffer contentBuffer;
    private int port = 8700;
    private HttpServletResponseImpl httpServletResponseImpl;
    private HttpServletRequestImpl httpServletRequestImpl;
    private final static String strDefaultFile = "c:/comet/src/index.html";
    private HttpRequestManager httpRequestManager = new HttpRequestManager();
    private Request request = null;
    private Response response = null;
    private CometContainer container = null;
    private static final Logger logger = Logger.getLogger(NonblockingSingleFileHTTPServer.class.getName());
    private CometState cometState;
    
    private NonblockingSingleFileHTTPServer(int port) {
        this.port = port;
        initializeContainer();
    }

    private void initializeContainer() {
      container = new CometContainer(httpRequestManager);
      cometState = CometState.getCometState();
    }

    private void run() throws IOException {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        ServerSocket serverSocket = serverChannel.socket();
        Selector selector = Selector.open();
        InetSocketAddress localPort = new InetSocketAddress(port);
        serverSocket.bind(localPort);
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
                 //      logger.log(Level.INFO, ".....ACCEPTING.....");
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel channel = server.accept();
                        channel.configureBlocking(false);
                        channel.register(selector, SelectionKey.OP_READ);
                    } else if (key.isWritable()) {
                    //    logger.log(Level.INFO, ".....WRITING.....");
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        if (buffer != null && buffer.hasRemaining()) {
                            channel.write(buffer);
                        } else {  // we're done
                            channel.close();
                        }
                    } else if (key.isReadable()) {
                    //    logger.log(Level.INFO, ".....READING......");
                        SocketChannel channel = (SocketChannel) key.channel();
                        //ByteBuffer buffer = ByteBuffer.allocate(4096);
                        //ByteBuffer readBuffer = ByteBuffer.allocate(4096);
                        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                        channel.read(readBuffer);
                        long time = System.currentTimeMillis();
                        processRequest(readBuffer, channel);
                        long now = System.currentTimeMillis();
                      //  System.out.println(" time taken:: " + (now - time));
                        // switch channel to write-only mode
                        key.interestOps(SelectionKey.OP_WRITE);
                        key.attach(contentBuffer.duplicate());
                    }
                } catch (IOException ex) {
                    key.cancel();
                    try {
                        key.channel().close();
                    } catch (IOException cex) {
                    }
                }
            }
        }
    }

    private ByteBuffer getDefaultContent() throws IOException {
        String strContent = null;
        strContent = getDefaultFIleContent();
        ByteBuffer buffer = ByteBuffer.allocate(strContent.length());
        byte[] headerData = strContent.getBytes("ASCII");
        buffer.put(headerData);
        buffer.flip();
        return buffer;
    }

    public static void main(String[] args){
        try {
            int port = 8700;
            NonblockingSingleFileHTTPServer server = new NonblockingSingleFileHTTPServer(port);
            server.run();
        } catch (Throwable ex){
            System.err.println(ex);
            logger.log(Level.SEVERE, ex.toString());
            ex.printStackTrace();
        }
    }

    private String getDefaultFIleContent(){
        String str = null;
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(strDefaultFile);
            str = StringUtils.convertStreamToString(fin);
        } catch (FileNotFoundException e) {
        }
        return str;
    }
    
    private void processContentWithContext() throws IOException{
        Object obj = container.processContent();
        if( obj instanceof StringBuilder){
            setContentBuffer((StringBuilder)obj);
            return;
        }
        if( obj instanceof ByteBuffer){
            setContentByteBuffer(((ByteBuffer)obj).array());
        }
    }
    
    public int getPort(){
      return port;
    }

    public void setPort(int port){
      this.port = port;
    }

    private void processRequest(ByteBuffer buffer, SocketChannel channel) throws IOException{
        request    = new Request();
        request.setCometState(cometState);
        response   = new Response();
        long start = System.currentTimeMillis();
        httpRequestManager.processHeaderDataFromRequest(buffer, request, response, channel);
        container.setRequest(request);
        container.setResponse(response);
        container.setContentBuffer(buffer);
        long end = System.currentTimeMillis();
//      System.out.println("--time taken to process processHeaderDataFromRequest1 " + (end - start));
        //System.out.println("...httpRequestManager.isContextPath()... " + httpRequestManager.isContextPath());
        if (httpRequestManager.isContextPath()){
            processContentWithContext();
            return;
        }
        contentBuffer = getDefaultContent();
    }

    private void setContentBuffer(StringBuilder sb){
        ByteBuffer buffer = ByteBuffer.allocate(sb.toString().length());
        byte[] headerData = null;
        try {
            headerData = sb.toString().getBytes("ASCII");
        } catch (UnsupportedEncodingException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        buffer.put(headerData);
        buffer.flip();
        if (this.contentBuffer != null) {
            this.contentBuffer.clear();
        }
        this.contentBuffer = buffer;
    }

    private void setContentByteBuffer(byte[] b) {
        if (b == null) {
            return;
        }
        HttpHeader hh = new HttpHeader();
        hh.setContentLength(b.length);
        hh.setContentType(Constants.image);
        StringBuilder sb = new StringBuilder();
        sb.append(hh.constructImageHttpResponse(200));
        byte[] headerData = null;
        try {
            headerData = sb.toString().getBytes("ASCII");
        } catch (UnsupportedEncodingException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        // constructing a single binary array
        byte[] image = new byte[b.length + headerData.length];
        System.arraycopy(headerData, 0, image, 0, headerData.length);
        System.arraycopy(b, 0, image, headerData.length, b.length);
        ByteBuffer buffer = ByteBuffer.allocate(image.length); //
        buffer.put(image);
        buffer.flip();
        if (this.contentBuffer != null) {
            this.contentBuffer.clear();
        }
        this.contentBuffer = buffer;
    }
}
