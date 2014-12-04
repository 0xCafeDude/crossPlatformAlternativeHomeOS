package edu.utah.cs6964.api;

import java.util.List;

public interface Module {
	public void start();
	public void stop();
	public void serviceRegistered(List<String> roles);
	public void serviceDeRegistered(List<String> roles);
	public String getModuleName();
}
