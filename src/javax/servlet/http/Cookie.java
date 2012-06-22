/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javax.servlet.http;

 public class Cookie {

	private String name = null;
	
	private String value = null;

	private String comment = null;
	
	private String domain = null;
	
	private int maxAge = 0;
	
	private String path = null;
	
	private boolean secured = true;
	
	private int version = 0;
	
		
public Cookie(String name,String value){
  this.name = name;
  this.value = value;
}
	
public Object clone(){
  return null;		
}
	
public String getComment(){
  return comment;
}
	
public String getDomain(){
 return domain;
}

/*
 * From http://www.w3.org/Protocols/rfc2109/rfc2109, cookies that are expired never
 * included in the request so they will be discarded.Basically, user agent will not
 * communicate the max-age of the cookie when it is sent to the server.So this method
 * always returns -1.
 */
public int getMaxAge(){
 return -1 ;
}
	
public String getName(){
 return name ;
}
	
	public String getPath(){
		return path ;
	}

	public boolean getSecure(){
		return secured ;
	}

	public String getValue(){
		return value ;
	}
	
	public int getVersion(){
		return version ;
	}
	
	// now setter begins
	public void setComment(String comment){
		this.comment = comment;
	}
	
	public void setDomain(String domain){
		this.domain = domain;
	}

	public void setMaxAge(int maxAge){
		this.maxAge = maxAge;
	}
	
	public void setPath(String path){
		this.path = path;
	}
	
	public void setSecure(boolean secured){
		this.secured = secured;
	}

	public void setValue(String value){
		this.value = value;
	}

	public void setVersion(int version){
		this.version = version;
	}

	
 }

