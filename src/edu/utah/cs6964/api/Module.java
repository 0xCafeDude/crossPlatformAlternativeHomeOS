package edu.utah.cs6964.api;

import java.util.List;
import java.util.Map;

import edu.utah.cs6964.roles.Role;

public interface Module {
	public void start();
	public void stop();
	public void serviceRegistered(List<String> roles);
	public void serviceDeRegistered(List<String> roles);
	public String getModuleId();
	public List<String> getOfferedRoles();
	public List<String> getRequiredRoles();
	public void setRequiredRoles(Map<String, Role> roleMap);
}
