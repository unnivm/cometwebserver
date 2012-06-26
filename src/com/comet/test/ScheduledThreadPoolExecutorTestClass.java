/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comet.test;
import java.util.concurrent.ScheduledThreadPoolExecutor;  
    import java.util.concurrent.TimeUnit;  
      
      
    public class ScheduledThreadPoolExecutorTestClass {  
      
        public static void main(String [] args){  
              
            ScheduledThreadPoolExecutor stpe = new ScheduledThreadPoolExecutor(5);  
            System.out.println(stpe.getCorePoolSize());  
            stpe.scheduleAtFixedRate(new Runnable() {  
                int count =0;   
                public void run() {  
                    System.out.print( System.currentTimeMillis()+ "  " + Thread.currentThread().getName() + " ---> ");  
                    count++;  
                    System.out.println(count);
                }  
            },0, 1,TimeUnit.SECONDS);  
              
        }  
          
    }  
