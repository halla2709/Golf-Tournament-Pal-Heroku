package project.persistence.entities;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "Scorecard") 
public class Scorecard {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToMany(cascade = CascadeType.ALL)
    @OrderBy("id ASC")
    private List<Round> rounds;

	@ManyToOne()
    @JoinColumn(name="player")
	private Golfer player;
	
    @ManyToOne()
    @JoinColumn(name="team")
	private Team team;
    
    private String course;

	private int numberOfRounds;

	public Scorecard(Golfer player, Team team, String course, int numberOfRounds){
		this.player = player;
		this.team = team;
		this.numberOfRounds = numberOfRounds;
		this.course = course;
		rounds = new ArrayList<Round>();
		for(int i = 0; i < numberOfRounds; i++) {
			rounds.add(new Round());
		}
	}
	
	public Scorecard(Long id, List<Round> rounds, Golfer player, Team team, String course, int numberOfRounds) {
		super();
		this.id = id;
		this.rounds = rounds;
		this.player = player;
		this.team = team;
		this.course = course;
		this.numberOfRounds = numberOfRounds;
	}



	public Scorecard() {
		super();
	}

	public int[] getTotalForRounds() { 
		int[] totals = new int[numberOfRounds];
		for(int i = 0; i < numberOfRounds; i++) {
			totals[i] = rounds.get(i).getTotal();
		}
		return totals;
	}
	
	public Golfer getPlayer() {
		return player;
	}

	public void setPlayer(Golfer player) {
		this.player = player;
	}

	public int getNumberOfRounds() {
		return numberOfRounds;
	}

	public void setNumberOfRounds(int numberOfRounds) {
		this.numberOfRounds = numberOfRounds;
	}
	
	public void setRound(int roundNumber, int[] scores) {
		rounds.get(roundNumber).setScore(scores);
	}
	
	public List<Round> getRounds() {
		return rounds;
	}

	public void setRounds(List<Round> rounds) {
		this.rounds = rounds;
	}
	
	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

}
