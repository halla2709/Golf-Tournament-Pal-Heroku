package project.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import project.persistence.entities.Golfer;
import project.persistence.entities.Tournament;
import project.persistence.entities.UserInfo;
import project.service.GolferService;
import project.service.Password;


/**
 * Small controller just to show that you can have multiple controllers
 * in your project
 */
@Controller
@RequestMapping("/")
public class MainController {
	
	GolferService golferService;
	
	@Autowired
	public MainController(GolferService golferService) {
		this.golferService = golferService;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(){

        return "index";
    }
    
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(){

        return "index";
    }
    
    
    @RequestMapping(value = "/mypage", method = RequestMethod.GET)
    public String mypage(){

        return "mypage";
    }
   
    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String about(){

        return "about";
    }
    
    @RequestMapping(value = "/json/registerUser", method = RequestMethod.GET)
    public @ResponseBody Golfer UserInfo(@RequestParam(value = "social") Long social,
    					 @RequestParam(value = "password") String password, 
    					 @RequestParam(value = "email") String email,
    					 @RequestParam(value = "handicap") Double handicap,
    					 @RequestParam(value = "name") String name){
    	//User
    	UserInfo userinfo = new UserInfo(social, password);
    	userinfo.setPassword(Password.md5(userinfo.getPassword()));
    	golferService.save(userinfo);
    	List<Golfer> friends = new ArrayList<Golfer>();
    	
    	//Golfer
    	Golfer golfer = new Golfer(name, social, handicap, email, friends);
    	golfer = golferService.save(golfer);
    	
    	return golfer;
    }
    
    @RequestMapping(value="/json/updateHandicap", method = RequestMethod.GET)
	public @ResponseBody void updateHandicap(@RequestParam(value = "social") long social, 
											 @RequestParam(value = "handicap") double handicap) {

    	Golfer golfer = golferService.findOne(social);
    	golfer.setHandicap(handicap);
    	System.out.println("Updated handicap =" + golfer.getHandicap());

	}
    
    @RequestMapping(value="/json/golfer", method = RequestMethod.GET)
	public @ResponseBody Golfer getGolfer(@RequestParam(value = "social") Long social) {

    	Golfer golfer = golferService.findOne(social);
		/*Golfer golfer = new Golfer("Halla", 2709942619L, 23.9, "gmail.com", null);
		golferService.addFriendForGolfer(golfer, new Golfer("Linda", 222222222L, 12.2, "linds.com", null));
		golferService.addFriendForGolfer(golfer, new Golfer("Unnur", 191919191L, 13.2, "unns.com", null));
		golferService.addFriendForGolfer(golfer, new Golfer("Hafrun", 221514236L, 12.2, "haffa.com", null));*/
    	System.out.println(golfer == null);
		
    	return golfer;

	}
    
    @RequestMapping(value="json/loginuser", method = RequestMethod.GET)
    public @ResponseBody Golfer loginUser(@RequestParam(value = "social") Long social,
    					 @RequestParam(value = "password") String password) {
    	
    	UserInfo user = golferService.findOneUser(social);
    	if(user.getPassword().equals(Password.md5(password))) {
    		return golferService.findOne(social);
    	}
    	else return null;
    	
    }

}