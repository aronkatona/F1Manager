package com.aronkatona.model;

import java.util.ArrayList;
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
@Table(name="USER")
public class User {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	private String password;
	
	private int money;
	
	private int points;
	
	@ManyToMany( fetch = FetchType.EAGER)
	@JoinColumn(name="userTeam_id")
	private List<Team> team;
	
	@ManyToMany( fetch = FetchType.EAGER)
	@JoinColumn(name="userDriver_id")
	private List<Driver> drivers;
	
	
	
	
	
	
	
	public User(){
		
	}
	
	public User(String name,String password){
		this.name = name;
		this.password = password;
		this.money = 1000;
		this.points = 0;
		this.team = new ArrayList<>();
		this.drivers = new ArrayList<>();
	}
	
	public User(String name, int money, int points){
		this.name = name;
		this.money = money;
		this.points = points;
		this.team = new ArrayList<>();
		this.drivers = new ArrayList<>();
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

	public void setMoney(int money){
		this.money = money;
	}
	
	public int getMoney(){
		return money;
	}
	
	public void addMoney(int money){
		this.money+= money;
	}
	
	public void removeMoney(int money){
		this.money-=money;
	}
	
	public void setPoints(int points){
		this.points = points;
	}
	
	public void setTeam(List<Team> team){
		this.team = team;
	}
	
	public List<Team> getTeam(){
		return team;
	}
	
	public void setDrivers(List<Driver> drivers){
		this.drivers = drivers;
	}
	
	public List<Driver> getDrivers(){
		return drivers;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPoints() {
		return points;
	}

	@Override
	public String toString(){
		return "id="+id+", name="+name+", money="+money+",  points="+points;
	}
} 