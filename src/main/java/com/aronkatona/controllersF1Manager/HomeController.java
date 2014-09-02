package com.aronkatona.controllersF1Manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.aronkatona.model.Driver;
import com.aronkatona.model.Race;
import com.aronkatona.model.Team;
import com.aronkatona.model.User;
import com.aronkatona.server.ServerThread;
import com.aronkatona.server.SessionLoader;
import com.aronkatona.service.DriverService;
import com.aronkatona.service.RaceService;
import com.aronkatona.service.TeamService;
import com.aronkatona.service.UserService;

@Controller
@SessionAttributes("notSuccessLogin")
public class HomeController {

	private Logger logger = Logger.getLogger(HomeController.class);

	private ServerThread serverThread = ServerThread.getInstance();

	private DriverService driverService;
	private TeamService teamService;
	private UserService userService;
	private RaceService raceService;

	@Autowired(required = true)
	@Qualifier(value = "driverService")
	public void setDriverService(DriverService ds) {
		this.driverService = ds;
		this.serverThread.setDriverService(ds);
	}

	@Autowired(required = true)
	@Qualifier(value = "teamService")
	public void setTeamService(TeamService ts) {
		this.teamService = ts;
		this.serverThread.setTeamService(ts);
	}

	@Autowired(required = true)
	@Qualifier(value = "userService")
	public void setUserService(UserService us) {
		this.userService = us;
		this.serverThread.setUserService(us);
	}

	@Autowired(required = true)
	@Qualifier(value = "raceService")
	public void setRaceService(RaceService rs) {
		this.raceService = rs;
		this.serverThread.setRaceService(rs);
	}
	
	@RequestMapping(value="/error404")
	public String errorPage(Locale locale){
		return "error";
	}

	@RequestMapping(value = "/welcome")
	public String welcome(Locale locale, Model model, HttpSession session,
			HttpServletRequest request) {
		System.out.println(locale);

		if (SessionLoader.getInstance()) {
			session.setAttribute("successLogin", "");
			session.setAttribute("notSuccessLogin", "");
			session.setAttribute("successSignup", "");
			session.setAttribute("notSuccessSignup", "");
		}

		if (session.getAttribute("userName") != null
				&& session.getAttribute("userName") != "") {
			// model.addAttribute("notSuccessLogin", "alreadyLoggedin");
			// session.setAttribute("notSuccessLogin", "alreadyLoggedin");
			model.addAttribute("successLogin",
					session.getAttribute("successLogin"));
			model.addAttribute("notSuccessLogin",
					session.getAttribute("notSuccessLogin"));
			model.addAttribute("successSignup",
					session.getAttribute("successSignup"));
			model.addAttribute("notSuccessSignup",
					session.getAttribute("notSuccessSignup"));
			model.addAttribute("userName", session.getAttribute("userName"));
			return "welcome";
		}

		model.addAttribute("successLogin", session.getAttribute("successLogin"));
		model.addAttribute("notSuccessLogin",
				session.getAttribute("notSuccessLogin"));
		model.addAttribute("successSignup",
				session.getAttribute("successSignup"));
		model.addAttribute("notSuccessSignup",
				session.getAttribute("notSuccessSignup"));

		return "welcome";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, @RequestParam Map<String, String> reqPar,
			HttpSession session) {

		String nameOfUser = reqPar.get("nameOfUser");
		String password = reqPar.get("passwordOfUser");
		boolean success = false;
		User user = null;
		for (User u : this.userService.listUsers()) {
			if (u.getName().equals(nameOfUser)
					&& u.getPassword().equals(password)) {
				user = u;
				success = true;
				break;
			}
		}

		if (session.getAttribute("userName") != null
				&& session.getAttribute("userName") != "") {
			model.addAttribute("notSuccessLogin", "alreadyLoggedin");
			session.setAttribute("notSuccessLogin", "alreadyLoggedin");
			// if(page.equals("welcome")) return "welcome";
			// if(page.equals("profile")) return "profile";
			return "welcome";
		}

		if (success) {
			session.setAttribute("userName", user.getName());
			model.addAttribute("userName", user.getName());
			model.addAttribute("successLogin", "successLogin");
			session.setAttribute("successLogin", "successLogin");

			logger.info("A " + user.getName() + " bejelentkezett!");

			// if(page.equals("welcome")) return "welcome";
			// if(page.equals("profile")) return "profile";
			return "welcome";
		} else {
			model.addAttribute("notSuccessLogin", "notSuccessLogin");
			session.setAttribute("notSuccessLogin", "notSuccessLogin");
			// if(page.equals("welcome")) return "welcome";
			// if(page.equals("profile")) return "profile";
			return "welcome";
		}

	}

	@RequestMapping(value = "/logout")
	public String logout(Model model, HttpSession session) {
		session.setAttribute("successLogin", "");
		session.setAttribute("notSuccessLogin", "");
		session.setAttribute("notSuccessSignup", "");
		session.setAttribute("userName", "");
		model.addAttribute("successLogin", "");
		model.addAttribute("notSuccessLogin", "");
		model.addAttribute("notSuccessSignup", "");

		// if(page.equals("welcome")) return "welcome";
		// if(page.equals("profile")) return "profile";
		return "welcome";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model, HttpSession session,
			@RequestParam Map<String, String> reqPar) {

		String nameOfUser = reqPar.get("nameOfUser");
		String password = reqPar.get("passwordOfUser");
		System.out.println(password);
		boolean success = true;
		for (User u : this.userService.listUsers()) {
			if (u.getName().equals(nameOfUser)) {
				success = false;
				break;
			}
		}

		if (success) {
			User user = new User(nameOfUser, password);
			this.userService.addUser(user);
			model.addAttribute("successLogin", "");
			model.addAttribute("notSuccessLogin", "");
			model.addAttribute("successSignup", "successSignup");
			model.addAttribute("successLogin", "");
			model.addAttribute("notSuccessLogin", "");
			model.addAttribute("successSignup", "successSignup");
			return "welcome";
		} else {
			model.addAttribute("notSuccessSignup", "notSuccessSignup");
			return "welcome";
		}

	}

	@ModelAttribute
	public Date raceTime(Model model) {
		Date actDate = new Date();
		boolean isNextRace = false;
		for (Race r : this.raceService.listRaces()) {
			if (r.getDate().after(actDate)) {
				isNextRace = true;
				DateTime date1 = new DateTime(actDate);
				DateTime date2 = new DateTime(r.getDate());
				Seconds seconds = Seconds.secondsBetween(date1, date2);
				model.addAttribute("nextRaceDate", seconds.getSeconds());
				model.addAttribute("nextRaceLocation", r.getLocation());
				System.out.println(r.getLocation());
				break;
			}
		}
		System.out.println("nextRace?: " + isNextRace);
		if (!isNextRace) {
			System.out.println("nincs next race");
			model.addAttribute("nextRaceLocation", "notNextRace");
		}

		return null;
	}

	@RequestMapping(value = "/profile")
	public String profile(Model model, HttpSession session) {

		if (session.getAttribute("userName") == null
				|| session.getAttribute("userName").equals("")) {
			model.addAttribute("successLogin",
					session.getAttribute("successLogin"));
			model.addAttribute("notSuccessLogin",
					session.getAttribute("notSuccessLogin"));
			model.addAttribute("successSignup",
					session.getAttribute("successSignup"));
			model.addAttribute("notSuccessSignup",
					session.getAttribute("notSuccessSignup"));
			model.addAttribute("firstLogin", "firstLogin");
			return "profile";
		} else {
			model.addAttribute("successLogin",
					session.getAttribute("successLogin"));
			model.addAttribute("notSuccessLogin",
					session.getAttribute("notSuccessLogin"));
			model.addAttribute("successSignup",
					session.getAttribute("successSignup"));
			model.addAttribute("notSuccessSignup",
					session.getAttribute("notSuccessSignup"));

			User user = null;
			for (User u : this.userService.listUsers()) {
				if (u.getName().equals(session.getAttribute("userName"))) {
					user = u;
					break;
				}
			}

			model.addAttribute("userName", user.getName());
			model.addAttribute("yourMoney", user.getMoney());
			model.addAttribute("yourPoints", user.getPoints());
			model.addAttribute("yourDrivers", user.getDrivers());
			model.addAttribute("yourTeams", user.getTeam());
			model.addAttribute("notEnoughMoneyToDriver",
					session.getAttribute("notEnoughMoneyToDriver"));
			model.addAttribute("notEnoughMoneyToTeam",
					session.getAttribute("notEnoughMoneyToTeam"));
			model.addAttribute("alreadyHaveThisTeam",
					session.getAttribute("alreadyHaveThisTeam"));
			model.addAttribute("alreadyHaveThisDriver",
					session.getAttribute("alreadyHaveThisDriver"));
			session.setAttribute("alreadyHaveThisDriver", "");
			session.setAttribute("alreadyHaveThisTeam", "");
			session.setAttribute("notEnoughMoneyToDriver", "");
			session.setAttribute("notEnoughMoneyToTeam", "");
			return "profile";
		}

	}

	@RequestMapping(value = "/drivers")
	public String drivers(Model model, HttpSession session) {
		model.addAttribute("buyDriver", session.getAttribute("buyDriver"));
		session.setAttribute("buyDriver", "");
		if (session.getAttribute("driver1Name") != null
				|| session.getAttribute("driver1Name") != "") {
			model.addAttribute("driver1Name",
					session.getAttribute("driver1Name"));
			session.setAttribute("driver1Name", "");
		}
		model.addAttribute("driverList", this.driverService.listDrivers());

		if (session.getAttribute("userName") != null
				&& session.getAttribute("userName") != "") {

			User user = null;
			for (User u : this.userService.listUsers()) {
				if (u.getName().equals(session.getAttribute("userName"))) {
					user = u;
					break;
				}
			}
			model.addAttribute("yourMoney", user.getMoney());
			model.addAttribute("userName", session.getAttribute("userName"));
		}

		model.addAttribute("successLogin", session.getAttribute("successLogin"));
		model.addAttribute("notSuccessLogin",
				session.getAttribute("notSuccessLogin"));
		model.addAttribute("successSignup",
				session.getAttribute("successSignup"));
		model.addAttribute("notSuccessSignup",
				session.getAttribute("notSuccessSignup"));

		return "drivers";
	}

	@RequestMapping(value = "/teams")
	public String teams(Model model, HttpSession session) {
		model.addAttribute("buyTeam", session.getAttribute("buyTeam"));
		session.setAttribute("buyTeam", "");
		if (session.getAttribute("team1Name") != null	|| session.getAttribute("team1Name") != "") {
			model.addAttribute("team1Name",	session.getAttribute("team1Name"));
			session.setAttribute("team1Name", "");
		}
		model.addAttribute("teamList", this.teamService.listTeams());

		if (session.getAttribute("userName") != null
				&& session.getAttribute("userName") != "") {

			User user = null;
			for (User u : this.userService.listUsers()) {
				if (u.getName().equals(session.getAttribute("userName"))) {
					user = u;
					break;
				}
			}
			model.addAttribute("yourMoney", user.getMoney());
			model.addAttribute("userName", session.getAttribute("userName"));
		}

		model.addAttribute("successLogin", session.getAttribute("successLogin"));
		model.addAttribute("notSuccessLogin",
				session.getAttribute("notSuccessLogin"));
		model.addAttribute("successSignup",
				session.getAttribute("successSignup"));
		model.addAttribute("notSuccessSignup",
				session.getAttribute("notSuccessSignup"));

		return "teams";
	}

	@RequestMapping(value = "/buyDriver")
	public String buyDriver(Model model, HttpSession session) {
		session.setAttribute("buyDriver", "buyDriver");
		User user = this.userService.getUserByName(session.getAttribute("userName").toString());

		Driver driver1 = null;
		if (user.getDrivers().size() != 0) {
			driver1 = user.getDrivers().get(0);
			session.setAttribute("driver1Name", driver1.getName());
		}

		return "redirect:/drivers";
	}

	@RequestMapping(value = "/buyTeam")
	public String buyTeam(Model model, HttpSession session) {
		session.setAttribute("buyTeam", "buyTeam");

		User user = this.userService.getUserByName(session.getAttribute("userName").toString());

		Team team1 = null;
		if (user.getTeam().size() != 0) {
			team1 = user.getTeam().get(0);
			session.setAttribute("team1Name", team1.getName());
		}
		return "redirect:/teams";
	}

	@RequestMapping(value = "/buyingTeam.{id}")
	public String buyTeamById(Model model, HttpSession session,
			@PathVariable int id) {
		session.setAttribute("buyTeam", "");
		User user = null;
		for (User u : this.userService.listUsers()) {
			if (u.getName().equals(session.getAttribute("userName"))) {
				user = u;
				break;
			}
		}

		List<String> teamNames = new ArrayList<>();
		for (Team team : user.getTeam()) {
			teamNames.add(team.getName());
		}

		Team team = this.teamService.getTeamById(id);
		for (String s : teamNames) {
			if (s.equals(team.getName())) {
				session.setAttribute("alreadyHaveThisTeam",
						"alreadyHaveThisTeam");
				return "redirect:/profile";
			}
		}

		if (user.getMoney() > team.getPrice()) {
			user.getTeam().add(team);
			user.removeMoney(team.getPrice());
			session.setAttribute("notEnoughMoneyToTeam", "");
			this.userService.updateUser(user);
		} else {
			session.setAttribute("notEnoughMoneyToTeam", "notEnoughMoneyToTeam");
		}

		return "redirect:/profile";
	}

	@RequestMapping(value = "/buyingDriver.{id}")
	public String buyDriverById(Model model, HttpSession session,
			@PathVariable int id) {
		session.setAttribute("buyDriver", "");
		User user = null;
		for (User u : this.userService.listUsers()) {
			if (u.getName().equals(session.getAttribute("userName"))) {
				user = u;
				break;
			}
		}

		List<String> driverNames = new ArrayList<>();
		for (Driver driver : user.getDrivers()) {
			driverNames.add(driver.getName());
		}

		Driver driver = this.driverService.getDriverById(id);
		for (String s : driverNames) {
			if (s.equals(driver.getName())) {
				session.setAttribute("alreadyHaveThisDriver",
						"alreadyHaveThisDriver");
				return "redirect:/profile";
			}
		}
		if (user.getMoney() > driver.getPrice()) {
			user.getDrivers().add(driver);
			user.removeMoney(driver.getPrice());
			session.setAttribute("notEnoughMoneyToDriver", "");
			this.userService.updateUser(user);
		} else {
			session.setAttribute("notEnoughMoneyToDriver",
					"notEnoughMoneyToDriver");
		}

		return "redirect:/profile";
	}

	@RequestMapping(value = "/sellDriver.{id}")
	public String sellDriver(Model model, HttpSession session,
			@PathVariable int id) {
		if (session.getAttribute("userName") == null
				|| session.getAttribute("userName").equals("")) {
			model.addAttribute("firstLogin", "firstLogin");
			return "profile";
		} else {

			User user = null;
			for (User u : this.userService.listUsers()) {
				if (u.getName().equals(session.getAttribute("userName"))) {
					user = u;
					break;
				}
			}

			Driver driver = this.driverService.getDriverById(id);
			int i = 0;
			for (Driver d : user.getDrivers()) {
				if (d.getName().equals(driver.getName())) {

					break;
				}
				++i;
			}
			user.getDrivers().remove(i);
			user.addMoney(driver.getPrice());
			this.userService.updateUser(user);

			model.addAttribute("userName", user.getName());
			model.addAttribute("yourMoney", user.getMoney());
			model.addAttribute("yourPoints", user.getPoints());
			model.addAttribute("yourDrivers", user.getDrivers());
			model.addAttribute("yourTeams", user.getTeam());
			return "profile";
		}
	}

	@RequestMapping(value = "/sellTeam.{id}")
	public String sellTeam(Model model, HttpSession session,
			@PathVariable int id) {

		if (session.getAttribute("userName") == null
				|| session.getAttribute("userName").equals("")) {
			model.addAttribute("firstLogin", "firstLogin");
			return "profile";
		} else {

			User user = null;
			for (User u : this.userService.listUsers()) {
				if (u.getName().equals(session.getAttribute("userName"))) {
					user = u;
					break;
				}
			}

			Team team = this.teamService.getTeamById(id);
			int i = 0;
			for (Team t : user.getTeam()) {
				if (t.getName().equals(team.getName())) {
					break;
				}
				++i;
			}
			user.getTeam().remove(i);
			System.out.println(user.getTeam().size());
			user.addMoney(team.getPrice());
			this.userService.updateUser(user);

			model.addAttribute("userName", user.getName());
			model.addAttribute("yourMoney", user.getMoney());
			model.addAttribute("yourPoints", user.getPoints());
			model.addAttribute("yourDrivers", user.getDrivers());
			model.addAttribute("yourTeams", user.getTeam());
			return "profile";
		}
	}

	/*************************************************
	 * 
	 * DB-hez
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Locale locale, Model model, HttpSession session) {
		model.addAttribute("asd", "morning");
		System.out.println(locale);

		/*
		 * session.setAttribute("successLogin","");
		 * session.setAttribute("notSuccessLogin","");
		 * session.setAttribute("successSignup","");
		 * session.setAttribute("notSuccessSignup","");
		 */
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

		return "redirect:/";
	}

	@RequestMapping(value = "/sizeOfLists")
	public String sizeOfLists(Model model) {

		List<Driver> drivers = this.raceService.getRaceById(1)
				.getResultOfDrivers();
		for (Driver driver : drivers) {
			System.out.println(driver.getName());
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/addTeamDriverRace")
	public String addTeamDriverRace(Model model) {

		Team team1 = new Team("ferrari", 100);
		Team team2 = new Team("mclaren", 80);
		Team team3 = new Team("mercedes", 120);
		Team team4 = new Team("redbull", 110);
		Team team5 = new Team("lotus", 50);
		this.teamService.addTeam(team1);
		this.teamService.addTeam(team2);
		this.teamService.addTeam(team3);
		this.teamService.addTeam(team4);
		this.teamService.addTeam(team5);

		Driver driver1 = new Driver("alonso", 800, 0);
		Driver driver2 = new Driver("kimi", 750, 0);
		driver1.setTeam(team1);
		driver2.setTeam(team1);
		Driver driver3 = new Driver("button", 500, 0);
		Driver driver4 = new Driver("magnussen", 450, 0);
		driver3.setTeam(team2);
		driver4.setTeam(team2);
		Driver driver5 = new Driver("hamilton", 1000, 0);
		Driver driver6 = new Driver("rosberg", 950, 0);
		driver5.setTeam(team3);
		driver6.setTeam(team3);
		Driver driver7 = new Driver("vettel", 850, 0);
		Driver driver8 = new Driver("ricciardo", 700, 0);
		driver7.setTeam(team4);
		driver8.setTeam(team4);
		Driver driver9 = new Driver("grosjean", 400, 0);
		Driver driver10 = new Driver("maldonado", 350, 0);
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

		Race race1 = new Race("melbourne", new Date(
				System.currentTimeMillis() + 120000));
		Race race2 = new Race("sepang", new Date(
				System.currentTimeMillis() + 240000));
		Race race3 = new Race("bahrain", new Date(
				System.currentTimeMillis() + 360000));
		this.raceService.addRace(race1);
		this.raceService.addRace(race2);
		this.raceService.addRace(race3);

		User user1 = new User("firstUser", "elso");
		User user2 = new User("secondUser", "masodik");
		this.userService.addUser(user1);
		this.userService.addUser(user2);

		return "redirect:/";
	}

}
