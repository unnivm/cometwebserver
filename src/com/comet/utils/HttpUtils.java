/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 package com.comet.utils;

 import java.util.HashMap;
 import java.util.Map;

 import com.comet.server.http.HttpRequestManager;
import java.util.*;

 public class HttpUtils {

	private HttpRequestManager requestManager;
	
	private static final String question="?";
	
	private static final String amp = "&";
	
	private static final String equals = "=";
	
	/**
	 * 
	 * @param requestManager
	 */
	public HttpUtils(HttpRequestManager requestManager) {
		this.requestManager = requestManager;
	}
	
	/**
	 * 
	 * @param strContext
	 * @return
	 */
	public static Map<String,Object> processQueryString(String str) {
		Map<String,Object> processMap = new HashMap<String,Object>(); 
		String queryString ;
		String [] token;
		System.out.println("QUERY STRING..... "+str);
		if(str.indexOf(question) != -1) {
		  queryString = str.substring(str.indexOf(question)+1, str.length());
		  // extracting key-value pairs from the query string
		  String [] requestMap = queryString.split(amp);
		  int size = (requestMap==null)?0:requestMap.length;
		  	if(size>0) { // if any query string found
			  for(int i=0; i<size; i++) {
				  // getting the key=value string
				  String strQuery = requestMap[i];
				  token = strQuery.split(equals);
				  processMap.put(token[0], token[1]);
			  }
		   } 
		}
		return processMap;
	}
	
   public static Map<String, String> processRequestHeader(StringTokenizer st){
      StringBuilder sb = new StringBuilder();
      HashMap<String,String> hm = new HashMap<String, String>();
      while(st.hasMoreTokens()){
          String s = st.nextToken();
         // System.out.println(" s " + s);
          if(s != null && s.length() > 0){
             if(s.indexOf(':')!= -1){ 
              String key   =  s.substring(0, s.indexOf(':'));
              String value =  s.substring(s.indexOf(':') + 1, s.length());
              hm.put(key, value);
              sb.append(s).append("\n");
             }else{
              hm.put("post",s); // we put POST params into the map
             }
          }
      }
      return hm;
   }
   
   public static Map<String, String> processRequestHeader(String s, String delimiter){
     long st = System.currentTimeMillis();
     Map<String,String> hm = new LinkedHashMap<String, String>();
     String sub = null;
     int i = 0;
     int j = s.indexOf(delimiter);  // First substring
     while( j >= 0)
     {
        sub = s.substring(i,j);
        if(sub.indexOf(':') != -1){
           String key   =  sub.substring(0, sub.indexOf(':')).trim();
           String value =  sub.substring(sub.indexOf(':') + 1, sub.length()).trim(); 
           hm.put(key, value);
        }
        i = j + 1;
        j = s.indexOf(delimiter, i);   // Rest of substrings
     }
     sub = s.substring(i); // Last substring
     if(sub.indexOf(':') != -1){
        String key   =  sub.substring(0, sub.indexOf(':')).trim();
        String value =  sub.substring(sub.indexOf(':') + 1, sub.length()).trim(); 
        hm.put(key, value);
     }else {
         hm.put("post",sub);
     }
     long end = System.currentTimeMillis();
     long tot = end - st;
     System.out.println(" time taken " + tot);
     return hm;
   }
   
    public static void main(String []arg) {
		String s ="abc.do?userid=span&pwd=123&abc=1-00";
		System.out.println("S.... "+s.substring(s.indexOf("?")+1, s.length()));
                     String header = 
                             "Host: localhost:8700\r\n"
                             + "User-Agent: Mozilla/5.0 (Windows NT 6.1; rv:12.0) Gecko/20100101 Firefox/12.0"
                + "\r\nAccept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"
                + "\r\nAccept-Language: en-us,en;q=0.5"
                + "\r\nAccept-Encoding: gzip, deflate"
                + "\r\nCookie: JSESSIONID=426d1a9a56a86c2367b82c04fc0f606e7e37d5b8"
                + "\r\nConnection: keep-alive"
                + "\r\nReferer: http://localhost:8700/unni/servlets/index.html"
                + "\r\nCache-Control: max-age=0"
                + "\r\nCache-Control: max-age=0"
                + "\r\nContent-Type: application/x-www-form-urlencoded"
                +"\r\nContent-Length: 28"
                +"\r\nfirstname=abc&lastname=test1";             
                             ;
                     Map<String,String> m = processRequestHeader(header, "\r\n");
         //System.out.println(m);
         System.out.println(m.get("Cookie") + "  " + m);
         long st = System.currentTimeMillis();
         Iterator iter = m.entrySet().iterator();
         while(iter.hasNext()){
             Map.Entry entry = (Map.Entry) iter.next();
                String key1  = entry.getKey().toString();
                String value = entry.getValue().toString(); 
               // System.out.println(" key " + entry + " value " + key1);
             }
         long end = System.currentTimeMillis();
         System.out.println(" time to itertae map " + (end - st));
         st = System.currentTimeMillis();
         iter = m.keySet().iterator();
         while(iter.hasNext()){
             String key = iter.next().toString();
             String value = m.get(key);
             //System.out.println(" key " + key + " value " + value);
         }
         end = System.currentTimeMillis();
         System.out.println(" time to itertae map " + (end - st));
	}
}

