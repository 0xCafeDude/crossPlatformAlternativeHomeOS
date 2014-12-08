package edu.utah.cs6964.modules;

import java.util.List;

import edu.utah.cs6964.exceptions.MissingRolesException;

public interface Module {
	public void start() throws MissingRolesException;
	public void stop();
	public void serviceRegistered(List<String> roles);
	public void serviceDeRegistered(List<String> roles);
	public String getModuleId();
	public List<String> getOfferedRoles();
}
