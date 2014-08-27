package com.aronkatona.controllersF1Manager;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.aronkatona.model.Driver;
import com.aronkatona.model.Race;
import com.aronkatona.model.Team;
import com.aronkatona.model.User;
import com.aronkatona.service.DriverService;
import com.aronkatona.service.RaceService;
import com.aronkatona.service.TeamService;
import com.aronkatona.service.UserService;


@Controller
public class HomeController {

	// private static final Logger logger =
	// LoggerFactory.getLogger(HomeController.class);

	private DriverService driverService;
	private TeamService teamService;
	private UserService userService;
	private RaceService raceService;
	
	@Autowired(required = true)
	@Qualifier(value = "driverService")
	public void setDriverService(DriverService ds) {
		this.driverService = ds;
	}
	
	@Autowired(required = true)
	@Qualifier(value = "teamService")
	public void setTeamService(TeamService ts) {
		this.teamService = ts;
	}
	
	@Autowired(required = true)
	@Qualifier(value = "userService")
	public void setUserService(UserService us) {
		this.userService = us;
	}
	
	@Autowired(required = true)
	@Qualifier(value = "raceService")
	public void setRaceService(RaceService rs) {
		this.raceService = rs;
	}
	
	@RequestMapping(value="/welcome")
	public String welcome(Model model,HttpSession session){
		
		if(session.getAttribute("userName") != null && session.getAttribute("userName") !=""){
			model.addAttribute("notSuccessLogin", "alreadyLoggedin");
			model.addAttribute("userName", session.getAttribute("userName"));
			return "welcome";
		}
		
		return "welcome";
	}
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login(Model model,@RequestParam Map<String,String> reqPar,HttpSession session){
		
		String nameOfUser = reqPar.get("nameOfUser");	
		boolean success = false;
		User user = null;
		for(User u : this.userService.listUsers()){
			if(u.getName().equals(nameOfUser)){
				user = u;
				success = true;
				break;
			}
		}
		
		if(session.getAttribute("userName") != null && session.getAttribute("userName") !=""){
		model.addAttribute("notSuccessLogin", "alreadyLoggedin");
		return "welcome";
		}
		
		if(success){
			session.setAttribute("userName", user.getName());
			model.addAttribute("successLogin", "successLogin");
			model.addAttribute("userName", user.getName());
			return "welcome";
		} 
		else{
			model.addAttribute("notSuccessLogin","notSuccessLogin");
			return "welcome";
		}	
	}
	
	@RequestMapping(value="/logout")
	public String logout(Model model, HttpSession session){
		session.setAttribute("userName", "");
		return "welcome";
	}
	
	@RequestMapping(value="/signup", method = RequestMethod.GET)
	public String signup(Model model, HttpSession session,@RequestParam Map<String,String> reqPar){
		String nameOfUser = reqPar.get("nameOfUser");	

		boolean success = true;
		for(User u : this.userService.listUsers()){
			if(u.getName().equals(nameOfUser)){
				success = false;
				break;
			}
		}
		
		if(success){
			User user = new User(nameOfUser, 890, 0);
			this.userService.addUser(user);
			model.addAttribute("successSignup", "successSignup");
			return "welcome";
		}
		else{
			model.addAttribute("notSuccessSignup", "notSuccessSignup");
			return "welcome";
		}
		
	}

	
	/*************************************************
	 * 
	 * DB-hez 
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Locale locale, Model model) {
		System.out.println("index");
		model.addAttribute("asd", "morning");
		return "index";
	}
	
	@RequestMapping(value = "/addDriversTeamsToRace")
	public String addDriversTeamsToRace(Model model) {
		
		Race race1 = this.raceService.getRaceById(1);
		System.out.println(race1.getResultOfDrivers().size());
		System.out.println(race1.getResultOfTeams().size());
		race1.getResultOfDrivers().add(this.driverService.getDriverById(7));
		race1.getResultOfDrivers().add(this.driverService.getDriverById(4));
		race1.getResultOfDrivers().add(this.driverService.getDriverById(8));
		race1.getResultOfDrivers().add(this.driverService.getDriverById(5));
		race1.getResultOfDrivers().add(this.driverService.getDriverById(3));
		race1.getResultOfDrivers().add(this.driverService.getDriverById(2));
		race1.getResultOfDrivers().add(this.driverService.getDriverById(1));
		race1.getResultOfTeams().add(this.teamService.getTeamById(2));
		race1.getResultOfTeams().add(this.teamService.getTeamById(1));
		race1.getResultOfTeams().add(this.teamService.getTeamById(3));
		race1.getResultOfTeams().add(this.teamService.getTeamById(4));
		this.raceService.updateRace(race1);
		
		Race race2 = this.raceService.getRaceById(2);
		System.out.println(race2.getResultOfDrivers().size());
		System.out.println(race2.getResultOfTeams().size());
		race2.getResultOfDrivers().add(this.driverService.getDriverById(5));
		race2.getResultOfDrivers().add(this.driverService.getDriverById(4));
		race2.getResultOfDrivers().add(this.driverService.getDriverById(1));
		race2.getResultOfDrivers().add(this.driverService.getDriverById(2));
		race2.getResultOfDrivers().add(this.driverService.getDriverById(10));
		race2.getResultOfDrivers().add(this.driverService.getDriverById(6));
		race2.getResultOfTeams().add(this.teamService.getTeamById(5));
		race2.getResultOfTeams().add(this.teamService.getTeamById(2));
		race2.getResultOfTeams().add(this.teamService.getTeamById(3));
		race2.getResultOfTeams().add(this.teamService.getTeamById(4));
		race2.getResultOfTeams().add(this.teamService.getTeamById(1));
		this.raceService.updateRace(race2);
		
		/*Race race = this.raceService.getRaceById(1);
		int tmp = 1;
		for(Driver d: race.getResultOfDrivers()){
			System.out.println(tmp++ + ". helyezes " + d.getName());
		}
		tmp = 1;
		System.out.println("-----------");
		for(Team t: race.getResultOfTeams()){
			System.out.println(tmp++ + ".helyez�s " + t.getName());
		}*/
		
		return "redirect:/";
	}
	
	@RequestMapping(value="/sizeOfLists")
	public String sizeOfLists(Model model){
		Race race1 = this.raceService.getRaceById(1);
		System.out.println(race1.getResultOfDrivers().size());
		for(Driver d: race1.getResultOfDrivers()){
			System.out.println(d.getName());
		}
		
		
		return "redirect:/";
	}
	
	@RequestMapping(value="/addTeamDriverRace")
	public String addTeamDriverRace(Model model){
		
		Team team1 = new Team("ferrari",100);
		Team team2 = new Team("mclaren",80);
		Team team3 = new Team("mercedes",120);
		Team team4 = new Team("redbull",110);
		Team team5 = new Team("lotus",50);
		this.teamService.addTeam(team1);
		this.teamService.addTeam(team2);
		this.teamService.addTeam(team3);
		this.teamService.addTeam(team4);
		this.teamService.addTeam(team5);
		
		Driver driver1 = new Driver("alonso", 800, 0);
		Driver driver2 = new Driver("kimi",750,0);
		driver1.setTeam(team1);
		driver2.setTeam(team1);
		Driver driver3 = new Driver("button",500,0);
		Driver driver4 = new Driver("magnussen",450,0);
		driver3.setTeam(team2);
		driver4.setTeam(team2);
		Driver driver5 = new Driver("hamilton",1000,0);
		Driver driver6 = new Driver("rosberg",950,0);
		driver5.setTeam(team3);
		driver6.setTeam(team3);
		Driver driver7 = new Driver("vettel",850,0);
		Driver driver8 = new Driver("ricciardo",700,0);
		driver7.setTeam(team4);
		driver8.setTeam(team4);
		Driver driver9 = new Driver("grosjean",400,0);
		Driver driver10 = new Driver("maldonado",350,0);
		driver9.setTeam(team5);
		driver10.setTeam(team5);
		this.driverService.addDriver(driver1);
		this.driverService.addDriver(driver2);
		this.driverService.addDriver(driver3);
		this.driverService.addDriver(driver4);
		this.driverService.addDriver(driver5);
		this.driverService.addDriver(driver6);
		this.driverService.addDriver(driver7);
		this.driverService.addDriver(driver8);
		this.driverService.addDriver(driver9);
		this.driverService.addDriver(driver10);
		
		
		Race race1 = new Race("melbourne",new Date(System.currentTimeMillis() + 120000));
		Race race2 = new Race("sepang",new Date(System.currentTimeMillis() + 240000));
		Race race3 = new Race("bahrain",new Date(System.currentTimeMillis() + 360000));
		this.raceService.addRace(race1);
		this.raceService.addRace(race2);
		this.raceService.addRace(race3);
		
		return "redirect:/";
	}
	


}
