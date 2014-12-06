package edu.utah.cs6964.applications;

import java.util.ArrayList;
import java.util.List;

import edu.utah.cs6964.management.ServiceManager;
import edu.utah.cs6964.modules.Module;
import edu.utah.cs6964.roles.applications.Application;

public class ApplicationManager {
	
	private ServiceManager serviceManger = ServiceManager.getInstance();
	private static ApplicationManager applManagerInstance = new ApplicationManager();
	private List<Application> applList = new ArrayList<Application>();
	
	private ApplicationManager() {
		
	}
	public static ApplicationManager getInstance() {
		return applManagerInstance;
	}
	
	public void loadAllApplications() {
		Application appl = new ApplicationForLightBulb();
		appl.setId("4");
		appl.setUserName(" This is an application to turn on and off a dimming light bulb");
		applList.add(appl);
		serviceManger.addModule((Module)appl);
	}
	
	public List<Application> getAllApplications() {
		return applList;
	}
	
}
