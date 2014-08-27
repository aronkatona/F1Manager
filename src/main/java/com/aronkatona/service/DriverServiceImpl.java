package com.aronkatona.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aronkatona.dao.DriverDAO;
import com.aronkatona.model.Driver;

@Service
public class DriverServiceImpl implements DriverService {
	
	private DriverDAO driverDAO;

	public void setDriverDAO(DriverDAO driverDAO) {
		this.driverDAO = driverDAO;
	}

	@Override
	@Transactional
	public void addDriver(Driver d) {
		this.driverDAO.addDriver(d);
	}

	@Override
	@Transactional
	public void updateDriver(Driver d) {
		this.driverDAO.updateDriver(d);
	}

	@Override
	@Transactional
	public List<Driver> listDrivers() {
		return this.driverDAO.listDrivers();
	}

	@Override
	@Transactional
	public Driver getDriverById(int id) {
		return this.driverDAO.getDriverById(id);
	}

	@Override
	@Transactional
	public void removeDriver(int id) {
		this.driverDAO.removeDriver(id);
	}

	@Override
	@Transactional
	public List<Driver> listDriversByTeamName(String teamName) {
		return this.driverDAO.listDriversByTeamName(teamName);
	}




} 