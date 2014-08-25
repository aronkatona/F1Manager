package com.aronkatona.service;

import java.util.List;

import com.aronkatona.model.Driver;

public interface DriverService {

	public void addDriver(Driver d);
	public void updateDriver(Driver d);
	public List<Driver> listDrivers();
	public Driver getDriverById(int id);
	public void removeDriver(int id);
	public List<Driver> listDriversByTeamName(String teamName);
	
} 