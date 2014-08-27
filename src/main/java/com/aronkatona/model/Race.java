package com.aronkatona.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="RACE")
public class Race {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private Date date;
	
	private String location;
	
	//versenyre jelentkezett userek
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="races")
	private List<User> users;
	
	//pilotak eredmenye
	@ManyToMany( fetch = FetchType.EAGER)
	@JoinColumn(name="resultOfDrivers_id")
	private List<Driver> resultOfDrivers;
	
	//csapatok helyezese
	@ManyToMany( fetch = FetchType.EAGER)
	@JoinColumn(name="resultOfTeam_id")
	private List<Team> resultOfTeams;
	
	public Race(){
		
	}
	
	public Race(String location, Date date){
		this.location = location;
		this.date = date;
		this.users = new ArrayList<>();
		this.resultOfDrivers = new ArrayList<>();
		this.resultOfTeams = new ArrayList<>();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List<Driver> getResultOfDrivers() {
		return resultOfDrivers;
	}

	public void setResultOfDrivers(List<Driver> resultOfDrivers) {
		this.resultOfDrivers = resultOfDrivers;
	}

	public List<Team> getResultOfTeams() {
		return resultOfTeams;
	}

	public void setResultOfTeams(List<Team> resultOfTeams) {
		this.resultOfTeams = resultOfTeams;
	}



	

}
