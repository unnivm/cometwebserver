/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javax.servlet.http;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Unni Vemanchery Mana
 */
public class SessionID {
    
    protected static synchronized String getSessionID(){
      return createSessionID();
    }
    
    private static String createSessionID(){
      byte[] result = null;
        try {
            SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");
            String randomNum  = new Integer( prng.nextInt() ).toString();
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            result            = sha.digest( randomNum.getBytes() );
        } catch (NoSuchAlgorithmException ex){
            Logger.getLogger(SessionID.class.getName()).log(Level.SEVERE, null, ex);
        }
      return hexEncode(result);
    }
    
    static private String hexEncode( byte[] aInput){
     StringBuilder result = new StringBuilder();
     char[] digits = {'0', '1', '2', '3', '4','5','6','7','8','9','a','b','c','d','e','f'};
     for ( int idx = 0; idx < aInput.length; ++idx ){
      byte b = aInput[idx];
      result.append( digits[ (b&0xf0) >> 4 ] );
      result.append( digits[ b&0x0f] );
     }
     return result.toString();
    }

    public static void main(String[] arg){
        String tmp = null;
        System.out.println(" start time " + System.currentTimeMillis());
        String id = getSessionID();
        System.out.println(" end time " + System.currentTimeMillis());
        System.out.println(" the session id " + id);
    }
}
