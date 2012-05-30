/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comet.container;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author unni_vm
 */
public class Test {
    
    public static void main(String []agr){
        String s = "/unni/servlets/servlet/HelloWorldExample";
        if(!s.contains(".class") || !s.contains(".jsp")){
            System.out.println("fff");
        }
        String [] str = s.split("/");
        System.out.println(str[1] + "......" + str.length);
        System.out.println(str[str.length - 1].indexOf("."));
        StringBuilder uri = new StringBuilder();
        for(int i = 2; i<=(str.length-1); i++){
            if(i != str.length-1)
            uri.append(str[i]).append("/");
            else uri.append(str[i]);
        }
        System.out.println(uri.toString());
    }
    
    private static void loadServlet(){
        try {
            String  path = new File(System.getProperty("user.dir")+"/applications/unni"+"/WEB-INF/classes").getCanonicalPath();
            File file = new File(path);
            URL url = file.toURL();
            URL [] urls = new URL[]{url};
            ClassLoader cl = new URLClassLoader(urls);
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
