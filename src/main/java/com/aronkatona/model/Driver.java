package com.aronkatona.model;

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
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="DRIVER")
public class Driver {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	private int price;
	
	private int points;
	
	@ManyToOne
	@JoinColumn(name="team_id")
	private Team team;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="drivers")
	private List<User> users;
	
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="resultOfDrivers")
	private List<Race> races;
	
	
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public int getPoints() {
		return points;
	}

	public Driver(){
		
	}
	
	public Driver(String name, int price, int points){
		this.name = name;
		this.price = price;
		this.points = points;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(int price){
		this.price = price;
	}
	
	public int getPrice(){
		return price;
	}
	
	public void setPoints(int points){
		this.points = points;
	}
	
	public void setTeam(Team team){
		this.team = team;
	}
	
	public Team getTeam(){
		return team;
	}
	@Override
	public String toString(){
		return "id="+id+", name="+name+", price="+price+",  points="+points;
	}
} 