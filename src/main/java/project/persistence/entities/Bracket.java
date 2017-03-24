	package project.persistence.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "Bracket") 
public class Bracket {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToMany(cascade = CascadeType.ALL)
	private List<Match> match;
	
	@ManyToMany()
	@JoinTable(name="BracketPlayer", joinColumns=@JoinColumn(name="bracket_id"), inverseJoinColumns=@JoinColumn(name="golfer_id")) 
	private List<Golfer> players;

	private String name;
	
	public Bracket(List<Golfer> players, String name){
		if(players == null) this.players = new ArrayList<Golfer>();
		else this.players = players;
		this.name = name;
	}
	
	public Bracket() {
		super();
	}

	public List<Match> getMatch() {
		return match;
	}

	public void setMatch(List<Match> match) {
		this.match = match;
	}

	public List<Golfer> getPlayers() {
		return players;
	}

	public void setPlayers(List<Golfer> players) {
		this.players = players;
	}

	public void addPlayer(Golfer player) {
		this.players.add(player);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String toString() {
		String s = "Bracket: " + getName();
		if (players == null) {
			s = s + "\n" + "This bracket has no players.";
			return s;
		}
		s = s + "\n" + "This bracket has players: ";
		
		for(int i = 0; i < players.size(); i++) {
			s = s + "\n" + players.get(i).getName();
		}
		return s;
	}

}
