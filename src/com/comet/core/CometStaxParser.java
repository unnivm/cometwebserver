/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comet.core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author unni_vm
 */
public class CometStaxParser {
    
    private static final String FILTER      = "filter";
    private static final CharSequence FILTERNAME  = "filter-name";
    private static final String FILTERCLASS = "filter-class";
    private static final String INITPARAM   = "init-param";
    
    private Filter filter = new Filter();
    
   
    private static void parse(String xmlFile) throws FileNotFoundException, XMLStreamException {
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
	InputStream in = CometStaxParser.class.getResourceAsStream(xmlFile);
        System.out.println(in);
	XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
        while(eventReader.hasNext()){
            XMLEvent event = eventReader.nextEvent();
            if (event.isStartElement()){
		StartElement startElement = event.asStartElement();
                if ((CharSequence)startElement.getName().getLocalPart()== FILTERNAME) {
                    event = eventReader.nextEvent(); 
                    System.out.println(" FILTER NAME :: " + event.asCharacters().getData());
                    continue;
                }
            }                                        
        }
        try {
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(CometStaxParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String [] arg){
        try {
            long start = System.currentTimeMillis();
            parse("web.xml");
            long end = System.currentTimeMillis();
            System.out.println(" time "+ (end-start));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CometStaxParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException ex) {
            Logger.getLogger(CometStaxParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static class Filter{
        
        private String filerName;
        private String filterClass;

        /**
         * @return the filerName
         */
        public String getFilerName() {
            return filerName;
        }

        /**
         * @param filerName the filerName to set
         */
        public void setFilerName(String filerName) {
            this.filerName = filerName;
        }

        /**
         * @return the filterClass
         */
        public String getFilterClass() {
            return filterClass;
        }

        /**
         * @param filterClass the filterClass to set
         */
        public void setFilterClass(String filterClass) {
            this.filterClass = filterClass;
        }
    }
    
    private class InitParam {
       private String paramName;
       private String paramValue;

        /**
         * @return the paramName
         */
        public String getParamName() {
            return paramName;
        }

        /**
         * @param paramName the paramName to set
         */
        public void setParamName(String paramName) {
            this.paramName = paramName;
        }

        /**
         * @return the paramValue
         */
        public String getParamValue() {
            return paramValue;
        }

        /**
         * @param paramValue the paramValue to set
         */
        public void setParamValue(String paramValue) {
            this.paramValue = paramValue;
        }
       
    }
}
