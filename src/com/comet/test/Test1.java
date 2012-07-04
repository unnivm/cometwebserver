/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comet.test;

/**
 *
 * @author gowri
 */
public class Test1 {
    
    public static void main(String []arg){
        String s = "She is sick .";
        String [] arr = s.split(" ");
        int size = arr.length;
        for(int i=1; i<size; i++){
          System.out.println(arr[i-1]+" "+arr[i]);
        }
        
        String s1 = "city^chennai|country^India~TamilNadu@pincode^600034";
        String [] arr1 = s1.split("[^\\w,]");
        int len = arr1.length;
        System.out.println("length  " + len);
        for(int i =0;i<len; i++)
            System.out.println(arr1[i]);
    }
}
