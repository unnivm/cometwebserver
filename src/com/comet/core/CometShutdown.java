
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comet.core;

import com.comet.container.CometContainer;
import com.comet.utils.CometUtil;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.DeflaterOutputStream;

/**
 *
 * @author unni_vm
*/
public class CometShutdown extends Thread{

    CometContainer cc;
    
    public CometShutdown(){
    }

    public CometShutdown(CometContainer cc){
      this.cc = cc;
    }
    
    @Override
    public void run(){
        FileOutputStream fos   = null;
        ObjectOutputStream oos = null;
        try {
            super.run();
            System.out.println(" terminating server....");
            if(cc.getRequest() == null)
                return;
            if(cc.getRequest().getCometState() == null)
                return;
            Map<String, SessionContext> map = cc.getRequest().getCometState().getValue();
            System.out.println(" map " + map);
            if(map.isEmpty()) return;
            fos = new FileOutputStream("session.ser");
            try{
               oos = new ObjectOutputStream(fos);
                oos.writeObject(map);
                oos.flush();
             }catch (IOException ex){
               Logger.getLogger(CometShutdown.class.getName()).log(Level.SEVERE, null, ex);
               ex.printStackTrace();
              }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CometShutdown.class.getName()).log(Level.SEVERE, null, ex);
        } 
        /*
        finally {
            try {
                if(oos != null)
                oos.close();
                if(fos != null)
                fos.close();
            } catch (IOException ex) {
                Logger.getLogger(CometShutdown.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        * 
        */
    }
   
}
