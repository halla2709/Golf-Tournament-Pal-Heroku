package project.persistence.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Team") 
public class Team{
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	private String teamName;
	
	private double handicap;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Golfer> players;
	
	public Team (String teamName, double handicap, List<Golfer> players){
		this.teamName = teamName;
		this.handicap = handicap;
		this.players = players;
	}
	
	public Team() {
		super();
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public double getHandicap() {
		return handicap;
	}

	public void setHandicap(double handicap) {
		this.handicap = handicap;
	}

	public List<Golfer> getPlayers() {
		return players;
	}

	public void setPlayers(List<Golfer> players) {
		this.players = players;
	}

}
