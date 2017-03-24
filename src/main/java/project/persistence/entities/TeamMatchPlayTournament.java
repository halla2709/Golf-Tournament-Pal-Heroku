package project.persistence.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TeamMatchPlayTournament")
@DiscriminatorValue("Team")
public class TeamMatchPlayTournament extends MatchPlayTournament {

	@OneToMany(cascade = CascadeType.ALL)
	private List<Team> teams;
	
	public TeamMatchPlayTournament(String course, String name, Date startDate, int numberOfRounds, List<Golfer> players, 
			boolean areBrackets, List<Bracket> brackets, PlayOffTree playOffs) {
		super(course, name, startDate, players, areBrackets, brackets, playOffs);
	}
	
	public TeamMatchPlayTournament(String course, String name, Date startDate, List<Golfer> players) {
		super(course, name, startDate, players);
	}

	public List<Team> getTeams() {
		return teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}
	
	
}
