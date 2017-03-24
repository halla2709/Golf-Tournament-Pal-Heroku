package project.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import project.persistence.entities.Golfer;
import project.persistence.entities.ScoreboardTournament;
import project.persistence.entities.Scorecard;

public class ScoreboardUpdater {

	ScoreboardTournament tournament;	
	int[][] score;
	
	public ScoreboardUpdater(ScoreboardTournament tournament) {
		this.tournament = tournament;
		score = new int[tournament.getPlayers().size()][tournament.getNumberOfRounds()+1];
		
		createScoreboard();
		updateStatus();
		tournament.setScores(score);
	}
	
	public static ScoreboardTournament createScoreboard(ScoreboardTournament tournament) {
		int[][] score = new int[tournament.getPlayers().size()][tournament.getNumberOfRounds()+1];
		
		for(int i = 0; i < tournament.getPlayers().size(); i++) {
			int[] scorecardi = tournament.getScorecards().get(i).getTotalForRounds();
			int total = 0;
			for(int j = 0; j < tournament.getNumberOfRounds(); j++) {
				tournament.getScorecards().get(i).getRounds().get(j).setScore();
				score[i][j] = scorecardi[j];
				total += scorecardi[j];
			}
			score[i][tournament.getNumberOfRounds()] = total;
		}
		tournament.setScores(score);
		return tournament;
	}
	
	private void createScoreboard() {
		score = new int[tournament.getPlayers().size()][tournament.getNumberOfRounds()+1];
		
		for(int i = 0; i < tournament.getPlayers().size(); i++) {
			int[] scorecardi = tournament.getScorecards().get(i).getTotalForRounds();
			int total = 0;
			for(int j = 0; j < tournament.getNumberOfRounds(); j++) {
				tournament.getScorecards().get(i).getRounds().get(j).setScore();
				score[i][j] = scorecardi[j];
				total += scorecardi[j];
			}
			score[i][tournament.getNumberOfRounds()] = total;
		}
		
	}
	
	private void updateStatus() {

		int[] totalscores = new int[tournament.getPlayers().size()];
		for(int i = 0; i < tournament.getPlayers().size(); i++) {
			totalscores[i] = score[i][tournament.getNumberOfRounds()];
		}
		
		ArrayIndexComparator comparator = new ArrayIndexComparator(totalscores);
		Integer[] indexes = comparator.createIndexArray();
		Arrays.sort(indexes, comparator);
		Arrays.sort(totalscores);

		
		List<Golfer> newplayers = new ArrayList<Golfer>();
		List<Scorecard> newscorecards = new ArrayList<Scorecard>();
		int[][] newscores = new int[tournament.getPlayers().size()][tournament.getNumberOfRounds() + 1];
		
		for(int i = 0; i < indexes.length; i++) {
			newplayers.add(tournament.getPlayers().get((int) indexes[i])); 
			newscorecards.add(tournament.getScorecards().get((int) indexes[i]));
			newscores[i] = score[(int) indexes[i]];
		}
		
		score = newscores;
		tournament.setPlayers(newplayers);
		tournament.setScores(newscores);
		tournament.setScorecards(newscorecards);
	}
	
	public ScoreboardTournament addScoresForRounds(long playerSocial, int[] scores, int round) {
		int index = 0;
		for(int i = 0; i < tournament.getPlayers().size(); i++) {
			if(tournament.getScorecards().get(i).getPlayer().getSocial() == playerSocial) {
				index = i;
				break;
			}
		}
		
		
		List<Scorecard> scorecards = tournament.getScorecards();
		scorecards.get(index).setRound(round, scores);
		int[] totals = scorecards.get(index).getTotalForRounds();
		tournament.setScorecards(scorecards);
		
		int total = 0;
		for(int i = 0; i < tournament.getNumberOfRounds(); i++) {
			score[index][i] = totals[i];
			total += score[index][i];
		}
		score[index][tournament.getNumberOfRounds()] = total;
		
		updateStatus();
		
		return tournament;
	}
	
	public ScoreboardTournament getTournament() {
		return tournament;
	}
}
