
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
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        DeflaterOutputStream dos   = null;
        try {
            super.run();
            System.out.println(" terminating server....");
            Map<String, ByteBuffer> map = cc.getRequest().getCometState().getValue();
            System.out.println(" map " + map);
            Iterator iterator = map.keySet().iterator();
            fos = new FileOutputStream("session.ser");
            dos = new DeflaterOutputStream(fos);
            while(iterator.hasNext()){
               String key         = iterator.next().toString();
               ByteBuffer buf     = map.get(key);
               SessionContext sc  = CometUtil.getHttpSession(buf.array());
                try {
                    oos = new ObjectOutputStream(dos);
                    oos.writeObject(sc);
                    oos.flush();
                }catch (IOException ex){
                  Logger.getLogger(CometShutdown.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CometShutdown.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                oos.close();
                dos.close();
               fos.close();
            } catch (IOException ex) {
                Logger.getLogger(CometShutdown.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
   
}
