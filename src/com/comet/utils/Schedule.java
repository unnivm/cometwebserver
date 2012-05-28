/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 package com.comet.utils;
 public class Schedule implements Runnable{
	    

		private int     iTime;
		private Thread  thread=null;
		private ITask   itask=null;
		private boolean status=true;

		
		public String threadname="";
		
		/**
		 * 
		 */
		Schedule(int iTime,ITask  itask){
		  this.iTime = iTime;
		  this.itask=itask;
		  this.iTime = 1000;
		  threadname="str";
		  //if(thread==null){
			 thread = new Thread(this,threadname);
		      thread.start();
		  //} 
		}
		
		/**
		 * @Description :This method repeatedly calls a certain task(s) 
		 */
		public void run() {
			//while(status){
				try{
				   //Thread.sleep(1000);
				   doATaskAtEveryNTheTime();
				}
				catch(Exception e){
					e.printStackTrace();
					thread=null;
				}
			//}
		}
		
		/**
		 * 
		 */
		private synchronized void doATaskAtEveryNTheTime(){
	     
	     //if(status){
	     System.out.println("Thread name "+thread.getName());	 
	     System.out.println("[3.Scheduler kicked off]");
	     itask.execute();
	     //stop();
	     //}
		}
		
		/**
		 * This method is to get the time in milli seconds
		 */
			
		private long getTimeInMilliSeconds(int iTime)
		{
			return iTime*60000;
		}
		
		public void stop() 
		{
			status=false;
			Thread.currentThread().interrupt();
		}
		
	}

