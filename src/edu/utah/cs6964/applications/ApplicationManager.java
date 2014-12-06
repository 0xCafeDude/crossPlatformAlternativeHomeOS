package edu.utah.cs6964.applications;

import edu.utah.cs6964.roles.applications.Application;

public class ApplicationManager {
	
	public void loadAllApplications() {
		Application appl = new ApplicationForLightBulb();
		appl.setId("4");
		appl.setUserName(" This is an application to turn on and off a dimming light bulb");
	}
	
}
