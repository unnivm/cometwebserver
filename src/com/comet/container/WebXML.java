/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comet.container;

import java.util.Map;

public class WebXML {

 private Map<String,Object> servlet;
 private Map<String,Object> servletMapping;
 private Map<String,Object> filter;
 private Map<String,Object> projectMap;
	 
 private boolean isUrlEncoded;
 
	 public Map<String, Object> getServlet() {
		return servlet;
	 }
	 public void setServlet(Map<String, Object> servlet) {
		this.servlet = servlet;
	 }
	 public Map<String, Object> getServletMapping() {
		return servletMapping;
	 }
	 public void setServletMapping(Map<String, Object> servletMapping) {
		this.servletMapping = servletMapping; 
	 }
	 public Map<String, Object> getFilter() {
		return filter;
	 }
	 public void setFilter(Map<String, Object> filter) {
		this.filter = filter;
	 }
	public Map<String, Object> getProjectMap() {
		return projectMap;
	}
	public void setProjectMap(Map<String, Object> projectMap) {
		this.projectMap = projectMap;
	}

    /**
     * @return the isUrlEncoded
     */
    public boolean isIsUrlEncoded() {
        return isUrlEncoded;
    }

    /**
     * @param isUrlEncoded the isUrlEncoded to set
     */
    public void setIsUrlEncoded(boolean isUrlEncoded) {
        this.isUrlEncoded = isUrlEncoded;
    }
}