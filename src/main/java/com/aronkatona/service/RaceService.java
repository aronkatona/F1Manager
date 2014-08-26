package com.aronkatona.service;

import java.util.List;

import com.aronkatona.model.Race;

public interface RaceService {

	public void addRace(Race r);
	public void updateRace(Race r);
	public List<Race> listRaces();
	public Race getRaceById(int id);
	public void removeRace(int id);
	
} 