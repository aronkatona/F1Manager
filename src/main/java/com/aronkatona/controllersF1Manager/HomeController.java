package com.aronkatona.controllersF1Manager;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aronkatona.model.Driver;
import com.aronkatona.model.Team;
import com.aronkatona.service.DriverService;
import com.aronkatona.service.TeamService;


@Controller
public class HomeController {

	// private static final Logger logger =
	// LoggerFactory.getLogger(HomeController.class);

	private DriverService driverService;
	private TeamService teamService;
	
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

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Locale locale, Model model) {
		System.out.println("index");
		model.addAttribute("asd", "morning");
		return "index";
	}

	@RequestMapping(value = "/addDriver")
	public String addDriver(Model model) {

		
		for(Driver d: this.driverService.listDrivers()){
			System.out.println(d.getTeam().getName());
		}
		
		
		return "redirect:/";
	}
	
	@RequestMapping(value="/addTeam")
	public String addTeam(Model model){
		
		
		Team t = new Team();
		t.setName("ferrari");
		t.setPoints(123);	
		this.teamService.addTeam(t);
		
		Driver driver1 = new Driver("alonso", 888, 777);
		Driver driver2 = new Driver("kimi",655,512);
		driver1.setTeam(t);
		driver2.setTeam(t);
		this.driverService.addDriver(driver1);
		this.driverService.addDriver(driver2);
		
	
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
