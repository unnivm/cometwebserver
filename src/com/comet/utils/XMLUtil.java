/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comet.utils;

import java.io.InputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;

 /**
  * This class is contains method that are used for XML manipulation.
  * @author unni_vm
  *
  */
 public final class XMLUtil {
 /**
  *  default private constructor.
  */
 private XMLUtil() {
 }
	 
	 /**
		 * @Description: This method will return the xml document from the input stream
		 * @param is
		 * @return document
		 */
 public static Document loadXML(final InputStream is) {
			Document document = null;
			try{
				InputSource src = new InputSource(is);
				DOMParser parser = new DOMParser();
				parser.parse(src);
				document = parser.getDocument();			
			}catch(Exception e){
				e.printStackTrace();
			}
			return document;
 }

 /**
  * 
  * @param document
  * @return
  */
 public static  NodeList getNodeList(final Document document,final String path) {
	 NodeList nodelist = document.getElementsByTagName(path);
	 return nodelist;
 }

 /**
  * 
  * @param nodelist
  * @param name
  * @return
  */
 public static Element getXMLElement(final NodeList nodelist, final String name ) {
	Element element = null;
	int size = (nodelist==null)?0:nodelist.getLength();
	for(int i=0; i<size; i++) {
		element = (Element)nodelist.item(i);
		if(name.equalsIgnoreCase(element.getNodeName())) {
			break;
		}
	}
	return element;
 }
 }

