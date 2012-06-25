/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javax.servlet;

/**
 *
 * @author unni_vm
 */
public interface SessionCookieConfig{
    String getComment();
    String getDomain();
    int getMaxAge();
    String getName();
    String getPath();
    boolean isHttpOnly();
    boolean isSecure();
    void setComment(String comment);
    void setDomain(String domain);
    void setHttpOnly(boolean httpOnly);
    void setMaxAge(int maxAge);
    void setName(String name);
    void setPath(String path);
    void setSecure(boolean secure);
}
