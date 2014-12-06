package edu.utah.cs6964.applications;

import java.util.ArrayList;
import java.util.List;

import edu.utah.cs6964.devices.zwave.RGBBulb;
import edu.utah.cs6964.exceptions.ModuleNotStartedException;
import edu.utah.cs6964.modules.Module;
import edu.utah.cs6964.roles.applications.Application;
import edu.utah.cs6964.roles.devices.Device;

public class ApplicationForLightBulb implements Module, Application {
	
	private String id, userName;
	private List<String> offeredRoles, requiredRoles;
	private Device rgbBulb;
	
	public ApplicationForLightBulb() {
		offeredRoles = new ArrayList<String>();
		offeredRoles.add("Application");
		requiredRoles = new ArrayList<String>();
		requiredRoles.add("DimmingLightBulb");
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getUserName() {
		return userName;
	}

	@Override
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public void start() {
		RGBBulb bulb = (RGBBulb)this.rgbBulb;
		try {
			bulb.turnOn();
			Thread.sleep(2000);
			bulb.turnOff();
		} catch (ModuleNotStartedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void stop() {
		// does nothing for now.
	}

	@Override
	public void serviceRegistered(List<String> roles) {
		// does nothing for now.
	}

	@Override
	public void serviceDeRegistered(List<String> roles) {
		// does nothing for now.
	}

	@Override
	public String getModuleId() {
		return getId();
	}

	@Override
	public List<String> getOfferedRoles() {
		return offeredRoles;
	}

	@Override
	public List<String> getRequiredRolesFromServiceManager() {
		return requiredRoles;
	}

}
