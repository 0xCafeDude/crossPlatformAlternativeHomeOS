package edu.utah.cs6964.roles.applications;

import edu.utah.cs6964.roles.Role;

public interface Application extends Role {
	
	public String getId();
	public void setId(String id);
	public String getUserName();
	public void setUserName(String userName);
}
