package com.aronkatona.controllersF1Manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aronkatona.model.Driver;
import com.aronkatona.model.Team;
import com.aronkatona.model.User;
import com.aronkatona.service.DriverService;
import com.aronkatona.service.TeamService;
import com.aronkatona.service.UserService;


@Controller
public class HomeController {

	// private static final Logger logger =
	// LoggerFactory.getLogger(HomeController.class);

	private DriverService driverService;
	private TeamService teamService;
	private UserService userService;
	
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

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Locale locale, Model model) {
		System.out.println("index");
		model.addAttribute("asd", "morning");
		return "index";
	}

	@RequestMapping(value = "/addDriver")
	public String addDriver(Model model) {
	
		/*Team team1 = new Team();
		team1.setName("ferrari");
		team1.setPoints(12);
		Team team2 = new Team();
		team2.setName("rbr");
		team2.setPoints(15);
		this.teamService.addTeam(team1);
		this.teamService.addTeam(team2);
	
		
		Driver driver1 = new Driver("alonso", 888, 777);
		Driver driver2 = new Driver("kimi",655,512);
		driver1.setTeam(team1);
		driver2.setTeam(team2);
		this.driverService.addDriver(driver1);
		this.driverService.addDriver(driver2);*/
		

		

		
		
		

		
		
		return "redirect:/";
	}
	
	@RequestMapping(value="/addTeam")
	public String addTeam(Model model){
		
		Team team1 = new Team();
		team1.setName("ferrari");
		team1.setPoints(17);
		Team team2 = new Team();
		team2.setName("mclaren");
		team2.setPoints(18);
		Team team3 = new Team();
		team3.setName("mercedes");
		team3.setPoints(12);
		this.teamService.addTeam(team1);
		this.teamService.addTeam(team2);
		this.teamService.addTeam(team3);
	
		
		Driver driver1 = new Driver("alonso", 888, 777);
		Driver driver2 = new Driver("kimi",655,512);
		driver1.setTeam(team1);
		driver2.setTeam(team1);
		Driver driver3 = new Driver("button",568,478);
		Driver driver4 = new Driver("magnussen",512,658);
		driver3.setTeam(team2);
		driver4.setTeam(team2);
		Driver driver5 = new Driver("hamilton",868,778);
		Driver driver6 = new Driver("rosberg",712,758);
		driver5.setTeam(team3);
		driver6.setTeam(team3);
		this.driverService.addDriver(driver1);
		this.driverService.addDriver(driver2);
		this.driverService.addDriver(driver3);
		this.driverService.addDriver(driver4);
		this.driverService.addDriver(driver5);
		this.driverService.addDriver(driver6);
		
	
		return "redirect:/";
	}
	
	@RequestMapping(value="/getDriversByTeam")
	public String getDriversByTeam(Model model){
		
		
		for(Driver d : this.teamService.listDriversById(1)){
			System.out.println(d.toString());
		}
		
		
		
		
		return "redirect:/";
	}

}
