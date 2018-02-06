package com.sp.datainitialization;

import java.io.Serializable;

/*An interface that have no member is known as marker or tagged interface. 
For example: Serializable, Cloneable, Remote,Threadsafe  They are used to provide some essential information to the JVM so 
that JVM may perform some useful operation.*/

public class DataInt implements Serializable {

	
	private static final long serialVersionUID = -5841500346110682370L;
	

	// ---Login------//
	
					public String username = "";
					public String userpassword = "";
					
	//----Login getter setters-------??				
					
					public String getUsername() {
						return username;
					}
					public void setUsername(String username) {
						this.username = username;
					}
					public String getUserpassword() {
						return userpassword;
					}
					public void setUserpassword(String userpassword) {
						this.userpassword = userpassword;
					}
					
}	