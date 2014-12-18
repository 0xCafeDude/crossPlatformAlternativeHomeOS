package edu.utah.cs6964.drivers;

import java.util.ArrayList;
import java.util.List;

import edu.utah.cs6964.drivers.zwave.ZWave;
import edu.utah.cs6964.management.ModulesManager;
import edu.utah.cs6964.roles.drivers.Driver;

public class DriverManager {
	private ModulesManager serviceManger = ModulesManager.getInstance();
	private List<Driver> driverList = new ArrayList<Driver>();
	private static DriverManager driverManagerInstance = new DriverManager();
	
	private DriverManager() {
		
	}
	
	public static DriverManager getInstance() {
		return driverManagerInstance;
	}
	
	public void loadAllDrivers() {
		ZWave zwave = ZWave.getInstance();
		zwave.setId("1");
		driverList.add(zwave);
		serviceManger.addModule(zwave);
	}
	
	public List<Driver> getAllDrivers() {
		return driverList;
	}
}
