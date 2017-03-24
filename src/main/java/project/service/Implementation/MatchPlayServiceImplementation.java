package project.service.Implementation;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.persistence.entities.Bracket;
import project.persistence.entities.Golfer;
import project.persistence.entities.Match;
import project.persistence.entities.MatchPlayTournament;
import project.persistence.entities.PlayOffRound;
import project.persistence.entities.PlayOffTree;
import project.persistence.repositories.MatchPlayCreatorRepository;
import project.service.MatchPlayCreator;
import project.service.MatchPlayService;

@Service
public class MatchPlayServiceImplementation implements MatchPlayService {

	MatchPlayCreatorRepository repository;
	
	@Autowired
	public MatchPlayServiceImplementation(MatchPlayCreatorRepository repository) {
		this.repository = repository;
	}
	
	/**
	 * Mi√∞a√∞ vi√∞ gefnar uppl√Ωsingar er athuga√∞ hvort h√¶gt s√© a√∞ setja upp m√≥t. Ef √æa√∞ er h√¶gt
	 * er m√≥ti√∞ vista√∞ √≠ gagnagrunninn. Annars er skila√∞ null.
	 */
	@Override
	public MatchPlayTournament save(boolean areBrackets, List<Golfer> players, int numInBracket, int numOutOfBrackets, String course, String name, Date startDate) {
		
		MatchPlayCreator creator = new MatchPlayCreator(areBrackets, players, numInBracket, numOutOfBrackets);
		if(!creator.playerNumberValidator()) return null;
		
		MatchPlayTournament newt = creator.createTournament(course, name, startDate);
		repository.save(newt);
		return newt;
	}

	@Override
	public void delete(MatchPlayTournament headontournament) {
		repository.delete(headontournament);
	}

	@Override
	public List<MatchPlayTournament> findAll() {
		return repository.findAll();
		
	}

	@Override
	public MatchPlayTournament findOne(Long id) {
		return repository.findOne(id);
	}

	@Override
	public PlayOffTree getPlayOffTree(Long id) {
		MatchPlayTournament tournament = findOne(id);
		return tournament.getPlayOffs();
	}

	@Override
	public List<Bracket> getBrackets(Long id) {
		MatchPlayTournament tournament = findOne(id);
		return tournament.getBrackets();
	}

	@Override
	public PlayOffTree addPlayoffMatchResults(Long id, Long playerSocial, Integer roundNum) {
		MatchPlayTournament tournament = findOne(id);
		PlayOffTree playoffs = tournament.getPlayOffs();
		PlayOffRound thisRound = playoffs.getRounds().get((int) roundNum);
		
		if(roundNum == playoffs.getRounds().size()-1) {
			return playoffs;
		}
		
		Golfer golfer = new Golfer();
		int matchIndex = 0;
		for(int i = 0; i < thisRound.getMatches().size(); i++) {
			if(thisRound.getMatches().get(i).getPlayers().get(0).getSocial() == (long) playerSocial) {
				golfer = thisRound.getMatches().get(i).getPlayers().get(0);
				matchIndex = i;
				break;
			}
			else if(thisRound.getMatches().get(i).getPlayers().get(1).getSocial() == (long) playerSocial) {
				golfer = thisRound.getMatches().get(i).getPlayers().get(1);
				matchIndex = i;
				break;
			}
		}
		
		PlayOffRound nextRound = playoffs.getRounds().get((int) (roundNum+1));
		nextRound.setMatches(sortByID(nextRound.getMatches()));
		List<Golfer> golfers = nextRound.getMatches().get(matchIndex/2).getPlayers();

		
		if(golfers == null) golfers = new ArrayList<Golfer>(2);
		if(golfers.size() < 2) {
			golfers.add(golfer);
			Match newMatch = nextRound.getMatches().get(matchIndex/2);
			newMatch.setPlayers(golfers);
			newMatch.setResults("ongoing");
			nextRound.setMatch(matchIndex/2, newMatch);
			playoffs.setRound(roundNum+1, nextRound);
			tournament.setPlayOffs(playoffs);
			
			tournament = repository.save(tournament);
			
		}
		
		return playoffs;
		
	}
	
	private List<Match> sortByID(List<Match> matches) {
		for(int i = 1; i < matches.size(); i++) {
			Match keymatch = matches.get(i);
	        Long key = matches.get(i).getid();
	        int k = i - 1;
	        while(k >= 0 && matches.get(k).getid() > key) {
	            matches.set(k+1, matches.get(k));
	            k--;
	        }
	        matches.set(k+1, keymatch);
	    }
		return matches;
	}
	
	@Override
	public Golfer findPlayer(PlayOffTree tree, Long social) {
		PlayOffRound thisRound = tree.getRounds().get(tree.getRounds().size()-1);
		
		Golfer golfer = new Golfer();
		for(int i = 0; i < thisRound.getMatches().size(); i++) {
			if(thisRound.getMatches().get(i).getPlayers().get(0).getSocial() == (long) social) {
				golfer = thisRound.getMatches().get(i).getPlayers().get(0);
				break;
			}
			else if(thisRound.getMatches().get(i).getPlayers().get(1).getSocial() == (long) social) {
				golfer = thisRound.getMatches().get(i).getPlayers().get(1);
				break;
			}
		}
		
		return golfer;
	}

	/**
	 * Niurstaan mun vera kennitala leikmanns : fjˆldi stiga sem leikmaurinn er me
	 * Match results er · forminu kt : niurstaa ˛ar sem kt er kennitala leikmannsins sem sigrai
	 * Ef engin niurstaa hefur veri sk· er results np
	 */
	@Override
	public HashMap<Long, Integer> getPlayerPoints(List<Bracket> brackets) {
		HashMap<Long, Integer> points = new HashMap<Long, Integer>();
		
		for(Bracket bracket : brackets) {
			for(Golfer player : bracket.getPlayers()) {
				points.put(player.getSocial(), 0);
			}
		}
		for(Bracket bracket : brackets) {
			for(Match match : bracket.getMatch()) {
				String results = match.getResults();
				if(!results.equals("np")) {
					Long winnerkt = Long.parseLong(results.substring(0, results.indexOf(" ")));
					points.put(winnerkt, points.get(winnerkt)+1);
				}
			}
		}
		return points; 
	}
	

	@Override
	public MatchPlayTournament save(MatchPlayTournament tournament) {
		return repository.save(tournament);
	}

	@Override
	public String[][] getBracketResults(List<Bracket> brackets, int numberOfPlayers) {
		String[][] resultTable = new String[brackets.get(0).getPlayers().size()*brackets.size()][brackets.get(0).getPlayers().size()];
		int bracketNum = 0;
		for(Bracket bracket : brackets) {
			int numberOfPlayersInBracket = bracket.getPlayers().size();
			for(int i = 0; i < numberOfPlayersInBracket; i++) {
				for(int j = i+1; j < numberOfPlayersInBracket; j++) {
					for(Match match : bracket.getMatch()) {
						if((match.getPlayers().get(0).getSocial() == bracket.getPlayers().get(i).getSocial()
								&& match.getPlayers().get(1).getSocial() == bracket.getPlayers().get(j).getSocial())
								||
								(match.getPlayers().get(0).getSocial() == bracket.getPlayers().get(j).getSocial()
								&& match.getPlayers().get(1).getSocial() == bracket.getPlayers().get(i).getSocial())) {
							String matchResults = match.getResults();
							if(matchResults.equals("np")) {
								resultTable[i+numberOfPlayersInBracket*bracketNum][j] = "np";
								resultTable[j+numberOfPlayersInBracket*bracketNum][i] = "np";
							}
							else {
								Long matchWinner = Long.parseLong(matchResults.substring(0,matchResults.indexOf(" ")));
								String winnerName = "";
								if(bracket.getPlayers().get(i).getSocial() == matchWinner) winnerName = bracket.getPlayers().get(i).getName();
								else if(bracket.getPlayers().get(j).getSocial() == matchWinner) winnerName = bracket.getPlayers().get(j).getName();
								resultTable[i+numberOfPlayersInBracket*bracketNum][j] = winnerName + " won:" + matchResults.substring(matchResults.indexOf(" "));
								resultTable[j+numberOfPlayersInBracket*bracketNum][i] = winnerName + " won:" + matchResults.substring(matchResults.indexOf(" "));
							}
						}						
					}
				}				
			}
			bracketNum++;
		}
		return resultTable;
	}

	@Override
	public List<Match> getPlayersToPlayOffTree(List<Bracket> brackets, int playersInTree) {
		HashMap<Long, Integer> playerPoints = this.getPlayerPoints(brackets);
		List<Golfer> treePlayers = new ArrayList<>();
		List<Match> firstRoundMatches = new ArrayList<>();
		for(Bracket bracket : brackets) {
			int playersInBracket = bracket.getPlayers().size();
			int playersFromThisBracket = 0;
			for(int i = playersInBracket-1; i > 0; i--) {
				for(Golfer player : bracket.getPlayers()) {
					if(playerPoints.get(player.getSocial()) == i) {
						System.out.println("Adding " + player.getName() + " to the playoffs.");
						treePlayers.add(player);
						playersFromThisBracket++;
						if(playersFromThisBracket == playersInTree/brackets.size()) break;
					}
					if(playersFromThisBracket == playersInTree/brackets.size()) break;
				}
				if(playersFromThisBracket == playersInTree/brackets.size()) break;
			}
		}
		
		for(int i = 0; i < treePlayers.size(); i += 4) {
			List<Golfer> inMatch0 = new ArrayList<> ();
			inMatch0.add(treePlayers.get(i));
			inMatch0.add(treePlayers.get(i+2));
			System.out.println(treePlayers.get(i).getName() + " vs " + treePlayers.get(i+2).getName());
			List<Golfer> inMatch1 = new ArrayList<> ();
			inMatch1.add(treePlayers.get(i+1));
			inMatch1.add(treePlayers.get(i+3));
			System.out.println(treePlayers.get(i+1).getName() + " vs " + treePlayers.get(i+3).getName());
			Match match0 = new Match(inMatch0, "playoffsnp", null);
			Match match1 = new Match(inMatch1, "playoffsnp", null);
			firstRoundMatches.add(match0);
			firstRoundMatches.add(match1);
			
		}
		return firstRoundMatches;
	}
}
