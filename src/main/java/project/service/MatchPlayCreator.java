package project.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import project.persistence.entities.Bracket;
import project.persistence.entities.Golfer;
import project.persistence.entities.MatchPlayTournament;
import project.persistence.entities.Match;
import project.persistence.entities.PlayOffRound;
import project.persistence.entities.PlayOffTree;

public class MatchPlayCreator {
	
	private boolean areBrackets;
	private List<Golfer> players;
	private int numInBracket;
	private int numOfBrackets;
	private int numOutOfBrackets;
	
	public MatchPlayCreator(boolean areBrackets, List<Golfer> players, int numInBracket, int numOutOfBrackets) {
		this.areBrackets = areBrackets;
		this.players = players;
		numOfBrackets = 0;
		if(areBrackets) this.numInBracket = numInBracket;
		else numInBracket = 0;
		if(areBrackets) this.numOutOfBrackets = numOutOfBrackets;
		else numOutOfBrackets = 0;
		sortByHandicap(players);
	}

	private void sortByHandicap(List<Golfer> unsorted) {
		Collections.sort(unsorted);
	}
	
	private double changeToBase2(int input) { 
		return Math.log(input)/Math.log(2);
	}
	
	/**
	 * Fjoldi leikmanna tarf að ganga upp í fjoldi ridla ef það eiga að vera ridlar.
	 * Einnig þarf fjoldi ridla að vera veldi af tveimur. Ef tad eru ekki ridlar tarf
	 * fjoldi leikmanna að vera veldi af tveimur.
	 * @return true ef fjoldinn passar, false annars.
	 */
	public boolean playerNumberValidator() {
		int playerNumber = players.size();
		
		if(areBrackets) {
			if(playerNumber < numInBracket) return false;
			double dNumOfBrackets = playerNumber/numInBracket;
			numOfBrackets = (int) Math.floor(dNumOfBrackets);
			// Mega ekki vera faerri en 2 ridlar og fjoldi leikmanna verdur ad ganga upp i ridlana
			if(numOfBrackets < 2 || numOfBrackets != dNumOfBrackets) return false;
			// Fjï¿½ldi riï¿½la verï¿½ur aï¿½ vera veldi af 2.
			else if(Math.floor(changeToBase2(numOfBrackets)) == changeToBase2(numOfBrackets)) return true;
			else return false;
			
		}
		
		else {
			if(Math.floor(changeToBase2(playerNumber)) == changeToBase2(playerNumber)) {
				return true;
			}
			else return false;
		}
		
	}
	
	private Bracket[] createBracket() {
		Bracket[] brackets = new Bracket[(int) numOfBrackets];
		for(int i = 0; i < numOfBrackets; i++) {
			// Riï¿½illinn hefur enn enga leikmenn en fï¿½r nafniï¿½ b0, b1,.. osfrv
			brackets[i] = new Bracket(null, "b" + i);
			for(int j = i; j < players.size(); j = j+2*numOfBrackets) {
				/* Eftir aï¿½ raï¿½aï¿½ er eftir forgjï¿½f inniheldur players
				 * [0,1,2,3,4,...,n-2,n-1]
				 * Viljum aï¿½ raï¿½aï¿½ sï¿½ svona ï¿½ riï¿½lana:
				 *		b0 	 b1  ... bm
				 *		0  	  1      m
				 *		2m-1	    m+1
				 * 		2m 	...
				 * 		...			n-1	
				 * 
				 *   T.d. ef sorted = {1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16} sem a ad rada i 4 ridla ta radast tad svona
				 *   		b1		b2		b3		b4
				 *   		1		2		3		4
				 *   		8		7		6		5
				 *   		9		10		11		12
				 *   		16		15		14		13
				 *   
				 *   b1		b2
				 *   0		1
				 *   3		2
				 *   4		5
				 *   7		6
				 *   8		9
				 *   11		10
				 */
				brackets[i].addPlayer(players.get(j));
				if(j+2*(numOfBrackets-i)-1 < players.size())
					brackets[i].addPlayer(players.get(j+2*(numOfBrackets-i)-1));
			}
		}
		brackets = addMatchesToBrackets(brackets);
		return brackets;
	}

	private Bracket[] addMatchesToBrackets(Bracket[] brackets) {
		for(int i = 0; i < brackets.length; i++) {
			List<Golfer> bracketPlayers = brackets[i].getPlayers();
			List<Match> matches = new ArrayList<Match>();
			for(int j = 0; j < bracketPlayers.size()-1; j++) {
				for(int k = j+1; k < bracketPlayers.size(); k++) {
					List<Golfer> matchGolfers = new ArrayList<>();
					matchGolfers.add(bracketPlayers.get(j));
					matchGolfers.add(bracketPlayers.get(k));
					Match newMatch = new Match(matchGolfers, "np", null);
					matches.add(newMatch);
				}
				
			}
			brackets[i].setMatch(matches);
		}
		return brackets;
	}
	
	private PlayOffTree createPlayOffTree(int numIn) {
		int numOfMatches = numIn/2;
		/*
		 * 	umferdir >
		 * 	leikir		0	1	2	3	...
		 *   ||		0
		 *   \/		1
		 *   		2
		 *   		...
		 *   numOfMatches er fjï¿½ldi leikja ï¿½ fyrstu umferï¿½
		 *   changeToBase2(numIn) er fjï¿½ldi umferï¿½a ï¿½.e. log2(fjï¿½ldi leikmanna ï¿½ fyrstu umferï¿½)
		 *   
		 */
		
		
		List<PlayOffRound> rounds = new ArrayList<PlayOffRound>((int) changeToBase2(numIn));
		// Ef ï¿½etta er ekki riï¿½lamï¿½t ï¿½ï¿½ er hï¿½gt aï¿½ stilla upp fyrstu umferï¿½inni
		for(int i = 0; i < ((int) changeToBase2(numIn)); i++) {
			List<Match> emptyMatches = new ArrayList<Match>(numOfMatches/(int) Math.pow(2, i)); 
			for(int j = 0; j < numOfMatches/(int) Math.pow(2, i); j++) {
				emptyMatches.add(new Match(null, "np", null));
			}
			rounds.add(new PlayOffRound(emptyMatches, i+1));
		}
		if(!areBrackets) {
			List<Match> emptyMatches = new ArrayList<Match>(numOfMatches);
			for(int i = 0; i < numOfMatches; i++) {
				// Rï¿½ï¿½um ï¿½ umferï¿½ina eftir forgjï¿½f.
				List<Golfer> playersInMatch = new ArrayList<Golfer>();
				playersInMatch.add(players.get(i));
				playersInMatch.add(players.get(players.size()-1-i));
				
				emptyMatches.add(i, new Match());
				emptyMatches.get(i).setPlayers(playersInMatch);
			}
			PlayOffRound p = new PlayOffRound(emptyMatches, 1);
			rounds.set(0,p);
		}
		
		return new PlayOffTree(rounds);
		
	}
	
	public MatchPlayTournament createTournament(String course, String name, Date startDate) {
		// Tjekkum hvort viï¿½ getum sett mï¿½tiï¿½ upp
		if(!playerNumberValidator()) return null;
		
		List<Bracket> brackets = new ArrayList<Bracket>();
		PlayOffTree playoffs = null;
		int numInPlayoffs = players.size();
		
		// Bï¿½um til riï¿½la ef ï¿½eir eiga aï¿½ vera. 
		// Fjï¿½ldi ï¿½ ï¿½tslï¿½ttarkeppni fer ï¿½ï¿½ eftir ï¿½vï¿½
		// hve margir komast upp ï¿½r riï¿½lunum.
		if(areBrackets) {
			brackets = Arrays.asList(createBracket());
			numInPlayoffs = numOutOfBrackets*brackets.size();
		}
		
		// Bï¿½um til ï¿½tslï¿½ttatrï¿½ï¿½
		playoffs = createPlayOffTree(numInPlayoffs);
		
		return new MatchPlayTournament(course, name, startDate, players, areBrackets, brackets, playoffs);
	}
	

	public boolean saveTournament(MatchPlayTournament tournament) {
		// Tenging viï¿½ gagnagrunn
		return true;
	}
	
	public void displayTournament(MatchPlayTournament tournament) {
		
	}
		
	public boolean areBrackets() {
		return areBrackets;
	}

	public void setAreBrackets(boolean areBrackets) {
		this.areBrackets = areBrackets;
	}

	public List<Golfer> getPlayers() {
		return players;
	}

	public void setPlayers(List<Golfer> players) {
		this.players = players;
	}

	public int getNumInBracket() {
		return numInBracket;
	}

	public void setNumInBracket(int numInBracket) {
		this.numInBracket = numInBracket;
	}

}