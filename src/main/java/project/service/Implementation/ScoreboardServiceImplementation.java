package project.service.Implementation;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.persistence.entities.Golfer;
import project.persistence.entities.Round;
import project.persistence.entities.ScoreboardTournament;
import project.persistence.entities.Scorecard;
import project.persistence.repositories.ScoreboardCreatorRepository;
import project.service.ScoreboardCreator;
import project.service.ScoreboardService;
import project.service.ScoreboardUpdater;

@Service
public class ScoreboardServiceImplementation implements ScoreboardService {

	ScoreboardCreatorRepository repository;
	
	@Autowired
	public ScoreboardServiceImplementation(ScoreboardCreatorRepository repository) {
		this.repository = repository;
	}
	
	/**
	 * Mi�a� vi� gefnar uppl�singar er athuga� hvort h�gt s� a� setja upp m�t. Ef �a� er h�gt
	 * er m�ti� vista� � gagnagrunninn. Annars er skila� null.
	 */
	@Override
	public ScoreboardTournament save(List<Golfer> players, int numberOfRounds, String course, String name, Date startDate) {
		
		ScoreboardCreator creator = new ScoreboardCreator(players, numberOfRounds, course, name, startDate);
		
		ScoreboardTournament newt = creator.createTournament();
		repository.save(newt);
		return newt;
	}

	@Override
	public void delete(ScoreboardTournament scoreboardtournament) {
		repository.delete(scoreboardtournament);
	}

	@Override
	public List<ScoreboardTournament> findAll() {
		return repository.findAll();
		
	}


	@Override
	public ScoreboardTournament findOne(Long id) {
		ScoreboardTournament scoreboard = repository.findOne(id);
		ScoreboardUpdater updater = new ScoreboardUpdater(scoreboard);
		return updater.getTournament();
	}

	@Override
	public Round getRound(Long id, long social, int round) {
		ScoreboardTournament tournament = findOne(id);
		List<Scorecard> scorecard = tournament.getScorecards();
		
		Round round2 = new Round();
		for (int i=0 ; i < scorecard.size() ; i++){
			if(scorecard.get(i).getPlayer().getSocial() == social) {
				round2 = scorecard.get(i).getRounds().get(round-1);
				break;
			}
		}	
		return round2;
	}

	@Override
	public ScoreboardTournament addRound(Long id, long social, int round, int[] scores) {
		ScoreboardTournament tournament = findOne(id);
		ScoreboardUpdater updater = new ScoreboardUpdater(tournament);
		tournament = updater.addScoresForRounds(social, scores, round-1);
		repository.save(tournament);
		return tournament;
	}


}
