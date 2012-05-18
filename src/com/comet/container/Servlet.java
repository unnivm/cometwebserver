/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comet.container;

public class Servlet {

	/**
	 * variable for SERVLET name
	 */
	private String  name;
	/**
	 * variable for SERVLET path
	 */
	private String  path;
	/**
	 * variable for singleThreaded 
	 */
	private String  singleThreaded;
	/**
	 * variable for loadonStartup value specified in WEB.XML;
	 */
	private int     loadonStartup;
	/**variable for initParameters defined in WEB.XML
	 * 
	 */
	private String  initParam;
	/**
	 * variable for SERVLET mapping defined in the WEB.XML
	 */
	private String  servletMapping;
	
	/**
	 * variable for project path in a web application
	 */
	private String projectpath;
	
	public String getName() {
		return name;
	}
	public void setName(final String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(final String path) {
		this.path = path;
	}
	public String getSingleThreaded() {
		return singleThreaded;
	}
	public void setSingleThreaded(final String singleThreaded) {
		this.singleThreaded = singleThreaded;
	}
	public int getLoadonStartup() {
		return loadonStartup;
	}
	public void setLoadonStartup(final int loadonStartup) {
		this.loadonStartup = loadonStartup;
	}
	public String getInitParam() {
		return initParam;
	}
	public void setInitParam(final String initParam) {
		this.initParam = initParam;
	}
	public String getServletMapping() {
		return servletMapping;
	}
	public void setServletMapping(final String servletMapping) {
		this.servletMapping = servletMapping;
	}
}

