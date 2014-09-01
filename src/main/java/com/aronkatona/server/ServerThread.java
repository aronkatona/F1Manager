package com.aronkatona.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Seconds;

import com.aronkatona.model.Driver;
import com.aronkatona.model.Race;
import com.aronkatona.model.Team;
import com.aronkatona.model.User;
import com.aronkatona.service.DriverService;
import com.aronkatona.service.RaceService;
import com.aronkatona.service.TeamService;
import com.aronkatona.service.UserService;

public class ServerThread extends Thread {

	private static ServerThread instance = null;
	private static final int PRICEOFPOINT = 200;
	
	private DriverService driverService;
	private TeamService teamService;
	private UserService userService;
	private RaceService raceService;
	
	public static ServerThread getInstance() {
	      if(instance == null) {
	         instance = new ServerThread();
	         instance.start();
	         return instance;
	      }
	      return instance;
	}
	
	
	@Override
	public void run(){
		
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		while(true){
			Date actDate = new Date();
			for(Race r : this.raceService.listRaces()){
				if(r.getDate().after(actDate)){
					DateTime date1 = new DateTime(actDate);
					DateTime date2 = new DateTime(r.getDate());
					Seconds seconds = Seconds.secondsBetween( date1, date2);
					System.out.println(seconds.getSeconds());
					
					try {
						Thread.sleep(seconds.getSeconds() * 1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					givePointsToUsers(r);
				}
			}
			
		}
	}
	
	public void givePointsToUsers(Race race){
		List<DriverPoints> driverPoints = resultOfDrivers(race);
	    List<TeamPoints> teamPoints = resultOfTeams(race);

		List<User> users = this.userService.listUsers();
		
		Driver driver1 = null;
		Driver driver2 = null;
		Team team1 = null;
		Team team2 = null;
		
		int driver1Point = 0;
		int driver2Point = 0;
		int team1Point = 0;
		int team2Point = 0;
		
		for(User user : users){
			if(user.getDrivers().size() == 2 && user.getTeam().size() == 2){
			    driver1 = user.getDrivers().get(0);
				driver2 = user.getDrivers().get(1);
				team1 = user.getTeam().get(0);
				team2 = user.getTeam().get(1);
				
				boolean driver1Done = false;
				boolean driver2Done = false; 
				boolean team1Done = false;
				boolean team2Done = false;
				for(DriverPoints dP : driverPoints){
					if(dP.getName().equals(driver1.getName())){
						user.addPoint(dP.getPoint());
						user.addMoney(dP.getPoint() * PRICEOFPOINT);
						driver1Point = dP.getPoint();
						driver1Done = true;
					}
					else if(dP.getName().equals(driver2.getName())){
						user.addPoint(dP.getPoint());
						user.addMoney(dP.getPoint() * PRICEOFPOINT);
						driver2Point = dP.getPoint();
						driver2Done = true;
					}
					
					if(driver1Done && driver2Done ){
						break;
					}
				}
				
				for(TeamPoints tP : teamPoints){
					if(tP.getName().equals(team1.getName())){
						user.addPoint(tP.getPoint());
						user.addMoney(tP.getPoint() * PRICEOFPOINT);
						team1Point = tP.getPoint();
						team1Done = true;
					}
					else if(tP.getName().equals(team2.getName())){
						user.addPoint(tP.getPoint());
						user.addMoney(tP.getPoint() * PRICEOFPOINT);
						team2Point = tP.getPoint();
						team2Done = true;
					}
					
					if(team1Done && team2Done ){
						break;
					}
				}
				this.userService.updateUser(user);
				
				
				
				
			}
			
		}
		
			driver1.addPoint(driver1Point);
			driver2.addPoint(driver2Point);
			team1.addPoint(team1Point);
			team2.addPoint(team2Point);
			this.driverService.updateDriver(driver1);
			this.driverService.updateDriver(driver2);
			this.teamService.updateTeam(team1);
			this.teamService.updateTeam(team2);
	}
	
	public List<DriverPoints> resultOfDrivers(Race race){
		List<DriverPoints> driverPoints = new ArrayList<>();
		
		int position = 1;
		
		for(Driver driver: race.getResultOfDrivers()){
			driverPoints.add(new DriverPoints(driver.getName(),position++));
		}
				
		return driverPoints;
	}
	
	public List<TeamPoints> resultOfTeams(Race race){
		List<TeamPoints> teamPoints = new ArrayList<>();
		
		int position = 1;
		
		for(Team team: race.getResultOfTeams()){
			teamPoints.add(new TeamPoints(team.getName(),position++));
		}
				
		return teamPoints;
	}
	
	
	public void setDriverService(DriverService driverService) {
		this.driverService = driverService;
	}
	public void setTeamService(TeamService teamService) {
		this.teamService = teamService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public void setRaceService(RaceService raceService) {
		this.raceService = raceService;
	}
	
	
	
}
