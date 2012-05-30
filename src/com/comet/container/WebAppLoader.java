/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comet.container;

import java.net.URL;
import java.net.URLClassLoader;

/**
 *
 * @author unni_vm
 */

/*
 * This class is used to pre-load all the web application under the 
 * Applications folder.The loaded classes will be stored in a BucketClass
 * object.This will avoid delay in loading the classes during runtime.
 */
public class WebAppLoader extends URLClassLoader {
    
    public WebAppLoader(URL[] urls) {
      super(urls);
    }
}

