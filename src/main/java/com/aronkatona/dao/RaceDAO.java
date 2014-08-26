package com.aronkatona.dao;

import java.util.List;

import com.aronkatona.model.Race;

public interface RaceDAO {

	public void addRace(Race r);
	public void updateRace(Race r);
	public List<Race> listRaces();
	public Race getRaceById(int id);
	public void removeRace(int id);
} 