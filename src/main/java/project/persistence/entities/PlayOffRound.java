package project.persistence.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Entity
public class PlayOffRound {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@OneToMany(cascade = CascadeType.ALL)
	@OrderBy("id ASC")
	List<Match> matches;
	
	int round;

	public PlayOffRound(List<Match> matches, int round) {
		this.matches = matches;
		this.round = round;
	}
	
	public PlayOffRound() {
		super();
	}

	public List<Match> getMatches() {
		return matches;
	}

	public void setMatches(List<Match> matches) {
		this.matches = matches;
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public void setMatch(int matchIndex, Match newMatch) {
		this.matches.set(matchIndex, newMatch);
	}

	
}
