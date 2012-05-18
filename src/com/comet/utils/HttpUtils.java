/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 package com.comet.utils;

 import java.util.HashMap;
 import java.util.Map;

 import com.comet.server.http.HttpRequestManager;

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
	
	public static void main(String []arg) {
		String s ="abc.do?userid=span&pwd=123&abc=1-00";
		System.out.println("S.... "+s.substring(s.indexOf("?")+1, s.length()));
	}
}

