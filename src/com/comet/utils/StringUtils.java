/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comet.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

 public class StringUtils {

	 static {
		 System.out.println(" this class loaded....");
	 }
	 public static String convertStreamToString(InputStream is) {
	        /*
	         * To convert the InputStream to String we use the BufferedReader.readLine()
	         * method. We iterate until the BufferedReader return null which means
	         * there's no more data to read. Each line will appended to a StringBuilder
	         * and returned as String.
	         */
	        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	        StringBuilder sb = new StringBuilder();
	 
	        String line = null;
	        try {
	            while ((line = reader.readLine()) != null) {
	                sb.append(line + "\n");
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                is.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	 
	        return sb.toString();
	    }
	
         public String appendString(String str, String delim){
             StringBuilder sb = new StringBuilder();
             return sb.toString();
         }
   
   /*
    * This method is used to return value of particular token
    * For example, if a string contains Host: localhost:8700
    * then, it will return localhost:8700.If no match found, it will 
    * return null;
    */
   public static String getSpecificStringFromList(List<String> list, String token){
    for(String s: list){
       if(s.startsWith(token)) return s.substring(s.indexOf(token) + token.length(), s.length());
    }
     return null;
   }
   
   public static String getSpecificStringFromList(String source, String token){
       if(source.startsWith(token)) return source.substring(source.indexOf(token) + token.length(), source.length());
       return null;
   }
   
 }

