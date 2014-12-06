package edu.utah.cs6964.management;

import java.util.ArrayList;
import java.util.List;

import edu.utah.cs6964.modules.Module;

public class ServiceManager {
	List<Module> systemModules = new ArrayList<Module>();
	private static ServiceManager serviceManagerInstance = new ServiceManager();
	
	private ServiceManager() {
		
	}
	
	public static ServiceManager getInstance() {
		return serviceManagerInstance;
	}
	
	public boolean addModule(Module module) {
		return systemModules.add(module);
	}
	
	public boolean removeModule(Module module) {
		return systemModules.remove(module);
	}
}
