/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comet.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author unni_vm
 */
public class TestServerConnection {
    private static List<StringBuilder> data = new ArrayList<StringBuilder>();
    public static void main(String []arg){
        connect("http://localhost:8700/unni/servlets/images/return.gif/");
        System.out.println("=============================" + new Date().toGMTString());
        System.out.println("=============================");
        connect("http://localhost:8080/unni/servlets/images/return.gif");
        System.out.println("=============================");
    }
    
    private static void connect(String uri){
        try{
         HttpURLConnection connection = (HttpURLConnection) new URL(uri).openConnection();
          System.out.println("header fields:::  " + connection.getHeaderFields());
                    StringBuilder content = new StringBuilder();
                    // read text returned by server
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = in.readLine()) != null) {
                      content.append(line +"\n");
                    }
                    System.out.println(content.toString());
                    in.close();
                    data.add(content);
    }
    catch(Exception e){
      e.printStackTrace();
    }
    }
}
