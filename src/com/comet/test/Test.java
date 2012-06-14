/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comet.test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author unni_vm
 */
public class Test {
 
    private static int avg;
    
    private static int cnt;
    
    public static void main(String []arg){
        String s ="";
        s+="GET /unni/servlets/images/return.gif HTTP/1.1\r\n"+
"Host: localhost:8700\r\n"+
"User-Agent: Mozilla/5.0 (Windows NT 6.1; rv:13.0) Gecko/20100101 Firefox/13.0\r\n"+
"Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n"+
"Accept-Language: en-us,en;q=0.5\r\n"+
"Accept-Encoding: gzip, deflate\r\n"+
"Connection: keep-alive\r\n"+
"If-Modified-Since: Tue, 17 Jan 2012 04:23:46 GMT\r\n"+
"If-None-Match: W/\"1231-1326774226001\"\r\n"+
"Cache-Control: max-age=0";
        
       // StreamTokenizer st = new StreamTokenizer(new StringReader("GET /unni/servlets/images/return.gif HTTP/1.1\r\nHost: localhost:8700"));
        StreamTokenizer st = new StreamTokenizer(new StringReader(s));
        StringTokenizer stz = new StringTokenizer(s, "\r\n");
        
        for(int i =0; i<10000; i++){
         //   parseReqHeaderWithStreamToken(st);
            parseReqHeaderWithStringToken(stz);
            stz = new StringTokenizer(s, "\r\n");
        }
        System.out.println(" average time taken : " + (float)(avg/cnt));
    }
    
    private static void parseReqHeaderWithStreamToken(StreamTokenizer st){
        long start = System.currentTimeMillis();
        st.resetSyntax();
        st.whitespaceChars('\n', '\n');
        st.whitespaceChars('\r', '\r');
        int token ;
        int line = 1;
        StringBuilder sb = new StringBuilder();
        try {
            while( (token = st.nextToken()) != StreamTokenizer.TT_EOF){
                if( line != st.lineno()) sb.append("\n");
                switch (token){
                case StreamTokenizer.TT_WORD:
                    sb.append((CharSequence)st.sval);
                    break;
                case StreamTokenizer.TT_NUMBER:
                     sb.append(st.nval);
                    break;
                default:
                     sb.append((char) token);
                    break;
            } 
             line = st.lineno();    
            }
            long now = System.currentTimeMillis();
            long tot = (now - start);
            System.out.println(((tot > 1)?"time taken stream token. Greate Stream token "+tot+" ms":""));
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void parseReqHeaderWithStringToken(StringTokenizer st){
        long start = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        HashMap<String,String> hm = new HashMap<String,String>();
        st.nextToken();
        while(st.hasMoreTokens()){
          String s = st.nextToken();
          String key   =  s.substring(0, s.indexOf(':'));
          String value =  s.substring(s.indexOf(':') + 1, s.length());
          hm.put(key, value);
          sb.append(s).append("\n");
        }
        long now = System.currentTimeMillis();
        long tot = (now - start);
       // System.out.println("map " + hm);
        if(tot > 0){
          avg+=tot;
          cnt++;
         System.out.println(hm + "  String token: Greate String token "+tot+" ms");
        }
    }

   private static void parseReqHeaderWithSplit(String s){
       
   } 
}
