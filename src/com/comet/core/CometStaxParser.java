/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comet.core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author unni_vm
 */
public class CometStaxParser extends DefaultHandler {
    
    private static final CharSequence FILTER      = "filter";
    private static final CharSequence FILTERNAME  = "filter-name";
    private static final CharSequence FILTERCLASS = "filter-class";
    private static final CharSequence INITPARAM   = "init-param";
    
    private Filter filter;
    
    private String tempVal;
    
     List<Filter> filters = new ArrayList<Filter>();
   
    private void parse(String xmlFile) throws FileNotFoundException, XMLStreamException {
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
	InputStream in = CometStaxParser.class.getResourceAsStream(xmlFile);
        System.out.println(in);
	XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
       
        int count = 1;
        while(eventReader.hasNext()){
            XMLEvent event = eventReader.nextEvent();
            if (event.isStartElement()){
		StartElement startElement = event.asStartElement();
                if (startElement.getName().getLocalPart() == FILTER) {
		    filter = new Filter();
                }                                   
                if((CharSequence)startElement.getName().getLocalPart()== FILTERNAME){
                    event = eventReader.nextEvent(); 
                    filter.setFilerName(event.asCharacters().getData());
                    continue;
                }
                if((CharSequence)startElement.getName().getLocalPart()== FILTERCLASS){
                    System.out.println(event.getEventType());
                    event = eventReader.nextEvent(); 
                    filter.setFilterClass(event.asCharacters().getData());
                    continue;
                }
                count++;
                
            }
            if(event.isEndElement()){
                EndElement endElement = event.asEndElement();
                 if (endElement.getName().getLocalPart() == FILTER) {
                     filters.add(filter);
                }  
            }
        }
        try {
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(CometStaxParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(count);
        for(Filter f: filters){
            System.out.println(" name  " + f.getFilerName());
            System.out.println(" class  " + f.getFilterClass());
        }
        filters.clear();
    }
    
    private void parseBySax(String xmlFile){
        SAXParserFactory spf = SAXParserFactory.newInstance();
        try {
			//get a new instance of parser
			SAXParser sp = spf.newSAXParser();
        		//parse the file and also register this class for call backs
			sp.parse(CometStaxParser.class.getResourceAsStream(xmlFile), this);
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch (IOException ie) {
			ie.printStackTrace();
		}
    }
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		//reset
		tempVal = "";
		if(qName.equalsIgnoreCase(FILTER.toString())) {
                        filter = new Filter();
		}
	}
	

    @Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		tempVal = new String(ch,start,length);
	}
	
    @Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

		if(qName.equalsIgnoreCase(FILTER.toString())) {
			filters.add(filter);
		}else if (qName.equalsIgnoreCase(FILTERNAME.toString())) {
			filter.setFilerName(tempVal);
		}else if (qName.equalsIgnoreCase(FILTERCLASS.toString())) {
			filter.setFilterClass(tempVal);
	       }
        }        
        
    public static void main(String [] arg){
        CometStaxParser csp = new CometStaxParser();
        try {
            long start = System.currentTimeMillis();
            csp.parse("web.xml");
            long end = System.currentTimeMillis();
            System.out.println(" time "+ (end-start));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CometStaxParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException ex) {
            Logger.getLogger(CometStaxParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("====PARSING BY SAX =======");
        long start = System.currentTimeMillis();
        csp.parseBySax("web.xml");
        long end = System.currentTimeMillis();
        System.out.println(" time taken to parse by SAX "+ (end-start));
        for(Filter f: csp.filters){
            System.out.println(" name  " + f.getFilerName());
            System.out.println(" class  " + f.getFilterClass());
        }
        
    }
    
    private static class Filter {
        
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
