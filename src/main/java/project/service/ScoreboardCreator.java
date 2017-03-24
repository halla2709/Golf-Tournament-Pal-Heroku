package project.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import project.persistence.entities.Golfer;
import project.persistence.entities.ScoreboardTournament;
import project.persistence.entities.Scorecard;

public class ScoreboardCreator {

	private List<Golfer> players;
	private int numberOfRounds;
	private String course;
	private String name;
	private Date startDate;
	private List<Scorecard> scorecards;
	private int[][] score;
	
	public ScoreboardCreator(List<Golfer> players, int numberOfRounds, String course, String name, Date startDate){
		this.players = players;
		this.numberOfRounds = numberOfRounds;
		this.course = course;
		this.name = name;
		this.startDate = startDate;
		this.scorecards = null;
	}
	
	public void createScorecards() {
		List<Scorecard> newscorecards = new ArrayList<Scorecard>();
		for(int i = 0; i <  players.size(); i++) {
			Scorecard s = new Scorecard(players.get(i), null, course, numberOfRounds);
			newscorecards.add(s);
		}
		setScorecards(newscorecards);
	}
	
	// ra�ir eru skor � leikmann, fyrri talan
	// d�lkar eru skor � hring, seinni talan
	// seinasti d�lkurinn er summa fyrir leikmanninn
	public void createScoreboard() {
		score = new int[players.size()][numberOfRounds+1];
		
		for(int i = 0; i < players.size(); i++) {
			int[] scorecardi = scorecards.get(i).getTotalForRounds();
			int total = 0;
			for(int j = 0; j <  numberOfRounds; j++) {
				score[i][j] = scorecardi[j];
				total += scorecardi[j];
			}
			score[i][numberOfRounds] = total;
		}
		
	}
	
	public ScoreboardTournament createTournament() { 
		
		createScorecards();
		createScoreboard();
		
		return new ScoreboardTournament(course, name, startDate, numberOfRounds, players, scorecards, score);
	}
	
	public List<Golfer>getPlayers() {
		return players;
	}

	public void setPlayers(List<Golfer> players) {
		this.players = players;
	}

	public int getNumberOfRounds() {
		return numberOfRounds;
	}

	public void setNumberOfRounds(int numberOfRounds) {
		this.numberOfRounds = numberOfRounds;
	}

	public List<Scorecard> getScorecards() {
		return scorecards;
	}

	public void setScorecards(List<Scorecard> scorecards) {
		this.scorecards = scorecards;
	}
}

