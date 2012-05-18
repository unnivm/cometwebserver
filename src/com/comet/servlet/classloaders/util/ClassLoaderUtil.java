/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comet.servlet.classloaders.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class ClassLoaderUtil {
	
    // Parameters
    private static final Class[] parameters = new Class[]{URL.class};

    /**
     * Add file to CLASSPATH
     * @param s File name
     * @throws IOException  IOException
     */
    public static void addFile(String s) throws IOException {
        File f = new File(s);
        addFile(f);
    }

    /**
     * Add file to CLASSPATH
     * @param f  File object
     * @throws IOException IOException
     */
    public static void addFile(File f) throws IOException {
        addURL(f.toURL());
    }

    /**
     * Add URL to CLASSPATH
     * @param u URL
     * @throws IOException IOException
     */
    public static void addURL(URL u) throws IOException {

        URLClassLoader sysLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        URL urls[] = sysLoader.getURLs();
        for (int i = 0; i < urls.length; i++) {
//            if (StringUtils.equalsIgnoreCase(urls[i].toString(), u.toString())) {
//                 return;
//            }
        }
        Class sysclass = URLClassLoader.class;
        
        try {
            Method method = sysclass.getDeclaredMethod("addURL", parameters);
            method.setAccessible(true);
            method.invoke(sysLoader, new Object[]{u});
        } catch (Throwable t) {
            t.printStackTrace();
            throw new IOException("Error, could not add URL to system classloader");
        }
    }
    
    /**
     * 
     * @param clazz
     * @return
     * @throws ClassNotFoundException
     */
    public static Class loadTheClass(String clazz) throws ClassNotFoundException {
    	return Class.forName(clazz);
    }
    
    /**
     * This method adds jars/ZIP files under a folder to java CLASSPATH.
     * Ex: addJarsToClasspath("c:/lib")
     * @param jarpath
     */
	public void addJarsToClasspath(final String jarpath) {
		try{
			Method addURL=null;
			addURL = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] {URL.class});
			addURL.setAccessible(true);
			File[] files = new File(jarpath).listFiles();
			ClassLoader cl = ClassLoader.getSystemClassLoader();
			for (int i = 0; i < files.length; i++) {
				URL url=null;
				url = files[i].toURL();
				addURL.invoke(cl, new Object[] { url });
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
    
}
