package project.persistence.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Match") 
public class Match {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @ManyToMany()
    @JoinTable(name="MatchPlayer", joinColumns=@JoinColumn(name="match_id"), inverseJoinColumns=@JoinColumn(name="golfer_id")) 
	private List<Golfer> players;
    
	private String results;
	
	private Date date;

	public Match () { 
		this.players = null;
		this.results = null;
		this.date = null;
	}
	
	public Match (List<Golfer> players, String results, Date date){
		this.players = players;
		this.results = results;
		this.date = date;
	}

	public List<Golfer> getPlayers() {
		return players;
	}

	public void setPlayers(List<Golfer> players) {
		this.players = players;
	}

	public String getResults() {
		return results;
	}

	public void setResults(String results) {
		this.results = results;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getid() { 
		return (Long) id;
	}
	public String toString() {
		return this.players.toString();
	}
}

