package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import project.persistence.entities.Golfer;
import project.persistence.entities.MatchPlayTournament;
import project.persistence.entities.ScoreboardTournament;
import project.service.GolferService;
import project.service.ScoreboardService;

@Controller
public class ScoreboardCreatorController {

	GolferService golferService;
	ScoreboardService scoreboardService;
	
	ScoreboardTournament tournament;
	boolean beenhere;
	
	@Autowired
	public ScoreboardCreatorController(GolferService golferService, ScoreboardService scoreboardService){
		this.golferService = golferService;
		this.scoreboardService = scoreboardService;
	}
	
    @RequestMapping(value = "/scoreboard", method = RequestMethod.GET)
    public String scoreboardViewGet(Model model){

        tournament = null;
        beenhere = false;
    	
        model.addAttribute("scoreboardTournament", new ScoreboardTournament());
        
        return "scoreboard";
    }
    
    @RequestMapping(value = "/prufaprufa", method = RequestMethod.POST)
    public String scoreboardViewPost(@ModelAttribute("golfer") Golfer golfer,
                                     Model model){

        // Save the Postit Note that we received from the form
        golferService.save(golfer);

        // Here we get all the Postit Notes (in a reverse order) and add them to the model
        model.addAttribute("golfers", golferService.findAll());

        // Add a new Postit Note to the model for the form
        // If you look at the form in PostitNotes.jsp, you can see that we
        // reference this attribute there by the name `postitNote`.
        model.addAttribute("golfer", new Golfer());

        // Return the view
        return "prufa";
    }



    @RequestMapping(value="/scoreboardprufa", method = RequestMethod.GET)
    public String scoreboardprufuGet(Model model) {
    	
    	model.addAttribute("scoreboard", new ScoreboardTournament());
    	
    	return "enneinprufa";
    }
    
    @RequestMapping(value="/addplayers2", method = RequestMethod.POST)
	public String addPlayersToMatchplayers(@ModelAttribute("scoreboardTournament") ScoreboardTournament scoreboardTournament,
											@ModelAttribute("golfer") Golfer golfer,
											Model model) {
		
		if(!beenhere) {
			tournament = scoreboardTournament;
			beenhere = true;
		}
		
		else {
    		tournament.addPlayer(golfer);
    		golferService.save(golfer);
    	}
    	
    	    	
    	model.addAttribute("golfer", new Golfer());
    	model.addAttribute("golfers", tournament.getPlayers());
    	
    	return "participant2";
	}

    @RequestMapping(value="/scoreboard2", method = RequestMethod.POST)
	public String showTournament(Model model) { 

		ScoreboardTournament newtournament = scoreboardService.save(tournament.getPlayers(), tournament.getNumberOfRounds(), tournament.getCourse(), tournament.getName(), tournament.getStartDate());
		
		model.addAttribute("golfers", newtournament.getPlayers());
		model.addAttribute("numberOfRounds", newtournament.getNumberOfRounds());
		model.addAttribute("course", newtournament.getCourse());
		model.addAttribute("startdate", newtournament.getStartDate());
		model.addAttribute("name", newtournament.getName());
		model.addAttribute("scoreboard", newtournament.getScores());
		model.addAttribute("id", newtournament.getid());
		beenhere = false;
		return "matchplay2";
	}
    
    @RequestMapping(value="/json/scoreboard", method = RequestMethod.POST)
    public @ResponseBody ScoreboardTournament postTournamentFromServer(@RequestBody ScoreboardTournament sentTournament,
			@RequestParam Long hostSocial) {
		Golfer host = golferService.findOne(hostSocial);
		
		// Adda playerum í gagnagrunn og sem vin. 
		golferService.addParticipantsFriendsForGolfer(host, sentTournament.getPlayers());
		
		ScoreboardTournament newTournament = scoreboardService.save(sentTournament.getPlayers(), sentTournament.getNumberOfRounds(),
				sentTournament.getCourse(), sentTournament.getName(), sentTournament.getStartDate());
		return newTournament;
    }
    
	
}
