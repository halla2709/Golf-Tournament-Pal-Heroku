package project.persistence.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (name = "TournamentSeries")

public class TournamentSeries{
	

	@Id
	private String name;
 
	@OneToMany(cascade = CascadeType.ALL)
	List<Tournament> tournaments;
	
	
	public TournamentSeries(String name, List<Tournament> tournaments){
		this.name = name;
	}
	
	@Column(name="tournamentSeries_name")
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public List<Tournament> getTournament(){
		return tournaments;
	}

	public void setTournament(List<Tournament> tournament){
		this.tournaments = tournament;
	}
}
