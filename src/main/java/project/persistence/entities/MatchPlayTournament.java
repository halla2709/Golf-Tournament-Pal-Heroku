package project.persistence.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MatchPlayTournament") 
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="TeamTournament")
public class MatchPlayTournament extends Tournament{


	private boolean areBrackets;
	
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Bracket> brackets;
	
	@OneToOne(cascade = CascadeType.ALL)
	private PlayOffTree playOffs;

	public MatchPlayTournament(){
		super();
	}
	
	public MatchPlayTournament(String course, String name, Date startDate, List<Golfer> players) {
		super(course, name, startDate, players);
	}

	public MatchPlayTournament(String course, String name, Date startDate, List<Golfer> players, 
			boolean areBrackets, List<Bracket> brackets, PlayOffTree playOffs) {
		super(course, name, startDate, players);
		this.areBrackets = areBrackets;
		this.brackets = brackets;
		this.playOffs = playOffs;
	}
	
	@Override
	public String toString() {
		return "HeadOnTournament [playOffs=" + playOffs + "]";
	}

	public boolean isAreBrackets() {
		return areBrackets;
	}

	public void setAreBrackets(boolean areBrackets) {
		this.areBrackets = areBrackets;
	}

	public List<Bracket> getBrackets() {
		return brackets;
	}

	public void setBrackets(List<Bracket> brackets) {
		this.brackets = brackets;
	}

	public PlayOffTree getPlayOffs() {
		return playOffs;
	}

	public void setPlayOffs(PlayOffTree playOffs) {
		this.playOffs = playOffs;
	}
}
