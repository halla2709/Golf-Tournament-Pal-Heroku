package project.persistence.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "PlayOffTree") 
public class PlayOffTree {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToMany(cascade = CascadeType.ALL)
    @OrderBy("id ASC")
	private List<PlayOffRound> rounds;
	
	public PlayOffTree() {
		super();
	}
	
	public PlayOffTree(List<PlayOffRound> rounds){
		this.rounds = rounds;
	}
	
	public List<PlayOffRound> getRounds() {
		return rounds;
	}
	
	public void setRounds(List<PlayOffRound> rounds) {
		this.rounds = rounds;
	}

	public void setRound(int roundNum, PlayOffRound rounds2) {
		this.rounds.set(roundNum, rounds2);
		
	}
}


