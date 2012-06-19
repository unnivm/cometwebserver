 package com.comet.utils;

 import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

 public class SchemaValidator {
 
	 private static boolean validation = false;
	 
	 private static String webXml = "C:/eclipse/Comet/src/com/server/utils/web.xml";
	 
	 private static Document document = null;
	 
	 private Map servletMap = new HashMap();
	 
	 private Map servletMapping = new HashMap();
	 
	 SchemaValidator schemaValidator = new SchemaValidator();
	 
	 // constructs a DOM from a web.xml file
	 static {
		 try{
			 	DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
			 	domFactory.setNamespaceAware(true); 
			 	DocumentBuilder builder = domFactory.newDocumentBuilder();
			 	document = builder.parse(new File(webXml));
		 }catch(Exception e){
			 
		 }
	 }
	 
	 public static void main(String []arg){
		SchemaValidator schemaValidator = new SchemaValidator();
		try {
			validateSchema("C:/eclipse/Comet/src/com/server/utils/web.xml","C:/eclipse/Comet/src/com/server/utils/web-app_2_5.xsd");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		NodeList servletnodes = getServletNode();
//		int size = (servletnodes==null)?0:servletnodes.getLength();
//		System.out.println(" size "+size);
//		for(int i=0; i<size; i++){
//			Node servlet = servletnodes.item(i);
//			if(servlet.getNodeType()==Node.ELEMENT_NODE){
//				Element elem =(Element) servlet;
//				System.out.println(elem.getNodeName());
//			}
//		}
		
		System.out.println(" .....getting all the servlets...");
		new SchemaValidator().getAllServlets();
		
	 }
	 
	private static boolean validateSchema(String strXmlName,String strSchemaName) throws IOException,SAXException, ParserConfigurationException{
		
	   SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
       File schemaLocation = new File(strSchemaName);
       Schema schema = factory.newSchema(schemaLocation);
       Validator validator = schema.newValidator();
       DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
       domFactory.setNamespaceAware(true); // never forget this
       DocumentBuilder builder = domFactory.newDocumentBuilder();
       Document doc = builder.parse(new File(strXmlName));
       DOMSource source = new DOMSource(doc);
       DOMResult result = new DOMResult();
       try {
           validator.validate(source, result);
           Document document = (Document) result.getNode();
           validation = true;
       }
       catch (SAXException ex) {
           System.out.println(strXmlName + " is not valid because ");
           System.out.println(ex.getMessage());
       }  
		 return validation;
	 }
	
	 private static boolean validateDTD(String strXMLName,String strDTDName){
		 try{
			 DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			 factory.setValidating(true);
			 DocumentBuilder builder = factory.newDocumentBuilder();
			 builder.setErrorHandler(new org.xml.sax.ErrorHandler() {
			 //To handle Fatal Errors
			 public void fatalError(SAXParseException exception)throws SAXException {
			 System.out.println("Line: " +exception.getLineNumber() + "\nFatal Error: "+exception.getMessage());
			 }
			 //To handle Errors
			 public void error(SAXParseException e)throws SAXParseException {
			 System.out.println("Line: " +e.getLineNumber() + "\nError: "+e.getMessage());
			 }
			 //To Handle warnings
			 public void warning(SAXParseException err)throws SAXParseException{
			 System.out.println("Line: " +err.getLineNumber() + "\nWarning: "+err.getMessage());
			 }
			 });
			 Document xmlDocument = builder.parse(new FileInputStream(strXMLName));
			 DOMSource source = new DOMSource(xmlDocument);
			 StreamResult result = new StreamResult(System.out);
			 TransformerFactory tf = TransformerFactory.newInstance();
			 Transformer transformer = tf.newTransformer();
			 transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, strDTDName);
			 transformer.transform(source, result);
			 validation = true;
			 }
			 catch (Exception e) {
				 e.printStackTrace();
			 System.out.println(e.getMessage());
			 }
			 return validation;
	 }
	 
	 /**
	  * 
	  * @return
	  */
	 public void getAllServlets(){
		 NodeList servletNodes = document.getElementsByTagName("servlet");
		 int servletNodesSize = (servletNodes==null)?0:servletNodes.getLength();
		 
		 for(int i=0; i<servletNodesSize; i++){
			 Element servlet = (Element) servletNodes.item(i);
			 
			 NodeList sname = servlet.getElementsByTagName("servlet-name");
			 NodeList servletClass = servlet.getElementsByTagName("servlet-class");
			 NodeList initParam = servlet.getElementsByTagName("init-param");
			 String servletname = "";
			 String servletclass = "";
			 
				 if(sname.item(0).getNodeType()==Node.ELEMENT_NODE){
					 System.out.println("sername "+sname.item(0).getFirstChild().getNodeValue());
					 servletname = sname.item(0).getFirstChild().getNodeValue();
				 }
				 
				 if(servletClass.item(0).getNodeType()==Node.ELEMENT_NODE){
					 System.out.println("servletClass "+servletClass.item(0).getFirstChild().getNodeValue());
					 servletclass = servletClass.item(0).getFirstChild().getNodeValue();
				 }
				 
				 servletMap.put(servletname, servletclass);
				 int size = (initParam==null)?0:initParam.getLength();
				 
				 for(int j=0; j<size; j++){
					 Element paramname = (Element) initParam.item(j);
					 NodeList paramName = paramname.getElementsByTagName("param-name");
					 NodeList paramValue = paramname.getElementsByTagName("param-value");
					 if(paramName.item(0).getNodeType()==Node.ELEMENT_NODE){
						 System.out.println("param-name value "+paramName.item(0).getFirstChild().getNodeValue());
					 }
					 if(paramValue.item(0).getNodeType()==Node.ELEMENT_NODE){
						 System.out.println("param-value value "+paramValue.item(0).getFirstChild().getNodeValue());
					 }
				 }
				     servletMapping.put("key", "paramValue");
		 }
		  
	 }
	 
	 public void getAllURLMapping(){
		 
	 }
	 
	 /**
	  * 
	  * @return
	  */
	 public NodeList getServletMappingNode(){
		 NodeList servletMappingNodes = document.getElementsByTagName("web-app/servlet-mapping");
		 return   servletMappingNodes ;
	 }
	 
	 
	 
 }
