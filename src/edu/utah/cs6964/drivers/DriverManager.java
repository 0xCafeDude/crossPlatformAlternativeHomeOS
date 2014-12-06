package edu.utah.cs6964.drivers;

import java.util.ArrayList;
import java.util.List;

import edu.utah.cs6964.drivers.zwave.ZWave;
import edu.utah.cs6964.management.ServiceManager;
import edu.utah.cs6964.roles.drivers.Driver;

public class DriverManager {
	private ServiceManager serviceManger = ServiceManager.getInstance();
	private List<Driver> driverList = new ArrayList<Driver>();
	
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
