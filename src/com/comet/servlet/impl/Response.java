/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comet.servlet.impl;

import java.io.OutputStream;

 public class Response {

 private OutputStream  outputStream;
		
 public Response(){
     
 }

 public OutputStream getOutputStream(){
   return outputStream;
}

public void setOutputStream(OutputStream outputStream) {
   this.outputStream = outputStream;
}


 }