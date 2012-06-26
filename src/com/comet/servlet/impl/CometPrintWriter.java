/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 package com.comet.servlet.impl;


 import java.io.*;

 public class CometPrintWriter extends PrintWriter {

   OutputStream outputStream;
   
   private  StringBuilder sb = new StringBuilder();
                
   public CometPrintWriter(File file) throws FileNotFoundException{
     super(file);
   }

   public CometPrintWriter(OutputStream outputStream) throws IOException {
     super(outputStream);
   }
   
   
   @Override
   public void println(String str){
     sb.append(str);
   }

  @Override
  public void print(String str){
    sb.append(str);
  }
        
  public String getData(){
    return sb.toString();
  }
	
  public void setData(String str) {
    sb.append(str);
  }
	
  @Override
  public void flush(){
    super.flush();
  }
  
 }
