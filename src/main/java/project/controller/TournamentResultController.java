package project.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import project.persistence.entities.Bracket;
import project.persistence.entities.Golfer;
import project.persistence.entities.Match;
import project.persistence.entities.MatchPlayTournament;
import project.persistence.entities.PlayOffTree;
import project.persistence.entities.Round;
import project.persistence.entities.ScoreboardTournament;
import project.persistence.entities.Tournament;
import project.service.GolferService;
import project.service.MatchPlayService;
import project.service.ScoreboardService;
import project.service.TournamentService;

@Controller
public class TournamentResultController {
	
	TournamentService tournamentService;
	MatchPlayService matchPlayService;
	ScoreboardService scoreboardService;
	GolferService golferService;
	
	
	@Autowired
    public TournamentResultController(TournamentService tournamentService, MatchPlayService matchPlayService, ScoreboardService scoreboardService,
    		GolferService golferService) {
		this.tournamentService = tournamentService;
		this.matchPlayService = matchPlayService;
		this.scoreboardService = scoreboardService;
		this.golferService = golferService;
	}
	
	
	@RequestMapping(value = "/results", method = RequestMethod.GET)
    public String results(Model model){
    	List<Tournament> tournaments = tournamentService.findAll();
    	if(tournaments == null) model.addAttribute("tournaents", null);
    	else model.addAttribute("tournaments", tournaments);
    	
        return "results";
    }
	
	
	@RequestMapping(value = "/json/results", method = RequestMethod.GET)
    public @ResponseBody List<Tournament> resultsjson(Model model){
    	List<Tournament> tournaments = tournamentService.findAll();
    	
        return tournaments;
    }
	
	@RequestMapping(value="/json/sendayfir", method=RequestMethod.GET)
	public @ResponseBody String yfir(@RequestParam(value="nafn") String nafn) {
		System.out.println(nafn);
		return nafn;
	}
	
	@RequestMapping(value="/tournament/{id}", method=RequestMethod.POST)
	public String addRoundToTournament(@PathVariable(value="id") Long id,
			@RequestParam(value="h1") int h1,
			@RequestParam(value="h2") int h2,
			@RequestParam(value="h3") int h3,
			@RequestParam(value="h4") int h4,
			@RequestParam(value="h5") int h5,
			@RequestParam(value="h6") int h6,
			@RequestParam(value="h7") int h7,
			@RequestParam(value="h8") int h8,
			@RequestParam(value="h9") int h9,
			@RequestParam(value="h10") int h10,
			@RequestParam(value="h11") int h11,
			@RequestParam(value="h12") int h12,
			@RequestParam(value="h13") int h13,
			@RequestParam(value="h14") int h14,
			@RequestParam(value="h15") int h15,
			@RequestParam(value="h16") int h16,
			@RequestParam(value="h17") int h17,
			@RequestParam(value="h18") int h18,
			@RequestParam(value="social") long social,
			@RequestParam(value="round") int round,
			Model model
			){
		int[] scores = {h1,h2,h3,h4,h5,h6,h7,h8,h9,h10,h11,h12,h13,h14,h15,h16,h17,h18};
		
		ScoreboardTournament tournament = scoreboardService.addRound(id, social, round, scores);
		
		model.addAttribute("golfers", tournament.getPlayers());
		model.addAttribute("course", tournament.getCourse());
		model.addAttribute("name", tournament.getName());
		model.addAttribute("startdate", tournament.getStartDate());
		model.addAttribute("id", tournament.getid());
		
	
		model.addAttribute("scoreboard", tournament.getScores());
		model.addAttribute("numberOfRounds", tournament.getNumberOfRounds());
		model.addAttribute("scorecards", tournament.getScorecards());
		
		return "scoreboardTournament";
	}
	
	@RequestMapping(value="/tournament/{id}", method=RequestMethod.GET)
	public String displayTournament(@PathVariable(value="id") Long id,
			Model model) {
		Tournament tournament = tournamentService.findOne(id);
		
		model.addAttribute("golfers", tournament.getPlayers());
		model.addAttribute("course", tournament.getCourse());
		model.addAttribute("name", tournament.getName());
		model.addAttribute("startdate", tournament.getStartDate());
		model.addAttribute("id", tournament.getid());
		
		if(tournament instanceof MatchPlayTournament) {
			return "matchPlayTournament";
		}
		else if(tournament instanceof ScoreboardTournament) {
			ScoreboardTournament stournament = scoreboardService.findOne(id);
			
			model.addAttribute("scoreboard", stournament.getScores());
			model.addAttribute("numberOfRounds", stournament.getNumberOfRounds());
			model.addAttribute("scorecards", stournament.getScorecards());
			return "scoreboardTournament";
		}
		
		return "tournament";
	}
	
	@RequestMapping(value="/json/tournament/{id}", method=RequestMethod.GET)
	public @ResponseBody Tournament displayjsontournament(@PathVariable(value="id") Long id) {
		Tournament tournament = tournamentService.findOne(id);
		if(tournament instanceof ScoreboardTournament) {
			ScoreboardTournament stournament = scoreboardService.findOne(id);
			return stournament;
		}
		return tournament;
	}
	

	
	
	@RequestMapping(value="/tournament/{id}/{social}/{round}", method=RequestMethod.GET)
	public String addRound(@PathVariable(value="id") Long id, 
			@PathVariable(value="social") long social,
			@PathVariable(value="round") int round,
			Model model) {
		
		Round round2= scoreboardService.getRound(id, social, round);
		model.addAttribute("scores", round2.getMyScores());
		model.addAttribute("social", social);
		model.addAttribute("round", round);
	
		return "roundInsert";
	}
	
	@RequestMapping(value="/tournament/{id}/playofftree", method=RequestMethod.GET)
	public String displayPlayoffs(@PathVariable(value="id") Long id,
			Model model) {
		
		PlayOffTree playoffs = matchPlayService.getPlayOffTree(id);
		
		model.addAttribute("rounds", playoffs.getRounds());
		model.addAttribute("numberOfRounds", playoffs.getRounds().size());
		model.addAttribute("numberOfMatches", Math.pow(2, playoffs.getRounds().size()-1));
		
		return "playoffs";
	}
	
	@RequestMapping(value="/tournament/{id}/playofftree", method=RequestMethod.POST)
	public String addPlayoffResults(@PathVariable(value="id") Long id,
			@RequestParam(value = "player", required=false) Long player,
			@RequestParam(value = "roundNum", required=false) Integer roundNum,
			Model model) {
		
		PlayOffTree playoffs = matchPlayService.addPlayoffMatchResults(id, player, roundNum);
		
		model.addAttribute("rounds", playoffs.getRounds());
		model.addAttribute("numberOfRounds", playoffs.getRounds().size());
		model.addAttribute("numberOfMatches", Math.pow(2, playoffs.getRounds().size()-1));
		if(playoffs.getRounds().size() == roundNum+1) {
			Golfer golfer = matchPlayService.findPlayer(playoffs, player);
			model.addAttribute("winner", golfer);
		}
		return "playoffs";
	}
	
	@RequestMapping(value="/tournament/{id}/brackets", method=RequestMethod.GET)
	public String displayBrackets(@PathVariable(value="id") Long id,
			Model model) {
		
		List<Bracket> brackets = matchPlayService.getBrackets(id);
		HashMap<Long, Integer> bracketResults = matchPlayService.getPlayerPoints(brackets);
		String[][] resultTable = matchPlayService.getBracketResults(brackets, bracketResults.size());
		
		model.addAttribute("brackets", brackets);
		model.addAttribute("numberOfBrackets", brackets.size());
		model.addAttribute("numberOfPlayersInBrackets", brackets.get(0).getPlayers().size());
		model.addAttribute("bracketResults", bracketResults);
		model.addAttribute("resultTable", resultTable);
		return "brackets";
	}
	
	@RequestMapping(value = "/tournament/{id}/{bracketID}_{player1social}_{player2social}", method=RequestMethod.GET)
	public String addBracketResults(@PathVariable(value="id") Long id,
			@PathVariable(value="bracketID") Long bracketID,
			@PathVariable(value="player1social") Long player1Social,
			@PathVariable(value="player2social") Long player2Social,
			Model model) {
		
		Golfer golfer1 = golferService.findOne(player1Social);
		Golfer golfer2 = golferService.findOne(player2Social);
		
		model.addAttribute("golfer1", golfer1);
		model.addAttribute("golfer2", golfer2);
		model.addAttribute("tournamentid", id);
		model.addAttribute("bracketId", bracketID);
		
		return "bracketResults";
	}
	
	@RequestMapping(value = "/tournament/{id}/{bracketID}_{player1social}_{player2social}", method=RequestMethod.POST)
	public String setBracketResults(@PathVariable(value="id") Long id,
			@PathVariable(value="bracketID") Long bracketID,
			@PathVariable(value="player1social") Long player1Social,
			@PathVariable(value="player2social") Long player2Social,
			@RequestParam(value="resulttext") String resulttext,
			@RequestParam(value="winner") Long winner) {
		
		MatchPlayTournament tournament = matchPlayService.findOne(id);
		List<Bracket> brackets = tournament.getBrackets();
		Bracket bracket = null;
		Match match = null;
		for(Bracket bracketi : brackets) {
			if(bracketi.getId() == bracketID) {
				bracket = bracketi;
				break;
			}
		}
		for(Match matchi : bracket.getMatch()) {
			if(matchi.getPlayers().get(0).getSocial() == player1Social) {
				if(matchi.getPlayers().get(1).getSocial() == player2Social) {
					match = matchi;
					break;
				}
			}
			else if(matchi.getPlayers().get(0).getSocial() == player2Social) {
				if(matchi.getPlayers().get(1).getSocial() == player1Social) {
					match = matchi;
					break;
				}
			}
		}
		
		match.setResults(winner + " " + resulttext);
		matchPlayService.save(tournament);
		
		
		return "redirect:/tournament/"+id+"/brackets";
	}
	
	@RequestMapping(value = "/tournament/{id}/process/playofftree", method=RequestMethod.POST)
	public String addPlayersToTree(@PathVariable(value="id") Long id) {
		MatchPlayTournament tournament = matchPlayService.findOne(id);
		List<Bracket> brackets = tournament.getBrackets();
		int numberInFirstRound = tournament.getPlayOffs().getRounds().get(0).getMatches().size();
		List<Match> firstRoundMatches = matchPlayService.getPlayersToPlayOffTree(brackets, numberInFirstRound*2);
		System.out.println(firstRoundMatches.size() + " to the playoffs");
		if(firstRoundMatches.size() == tournament.getPlayOffs().getRounds().get(0).getMatches().size()) {
			tournament.getPlayOffs().getRounds().get(0).setMatches(firstRoundMatches);
			matchPlayService.save(tournament);
			return "redirect:/tournament/"+id+"/playofftree";
		}
		return "redirect:/tournament/"+id+"/brackets";
	}
	
	@RequestMapping(value = "/json/search")
	public @ResponseBody List<Tournament> searchByName(@RequestParam String searchName) {
		if(searchName == null) {
			return tournamentService.findAll();
		}
		return tournamentService.findByName(searchName);
	}
	
	@RequestMapping(value = "/json/getTournament", method = RequestMethod.GET)
	public @ResponseBody Tournament searchByID(@RequestParam Long id) {
		return tournamentService.findOne(id);
	}
}
