package project.service.Implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.persistence.entities.ScoreboardTournament;
import project.persistence.entities.Tournament;
import project.persistence.repositories.MatchPlayCreatorRepository;
import project.persistence.repositories.ScoreboardCreatorRepository;
import project.persistence.repositories.TournamentRepository;
import project.service.ScoreboardUpdater;
import project.service.TournamentService;

@Service
public class TournamentServiceImplementation implements TournamentService {
	
	TournamentRepository repository;
	ScoreboardCreatorRepository scoreRepository;
	MatchPlayCreatorRepository matchRepository;
	
	@Autowired
	public TournamentServiceImplementation(TournamentRepository repository, ScoreboardCreatorRepository scoreRepository, MatchPlayCreatorRepository matchRepository) {
		this.repository = repository;
		this.scoreRepository = scoreRepository;
		this.matchRepository = matchRepository;
	}
	
	@Override
	public List<Tournament> findAll() {
		return repository.findAll();
	}

	@Override
	public Tournament findOne(Long id) {
		
		Tournament tournament = scoreRepository.findOne(id);
		if(tournament != null) {
			ScoreboardTournament stournament = (ScoreboardTournament) tournament;
			tournament = ScoreboardUpdater.createScoreboard(stournament);
			return stournament;
		}
		else {
			tournament = matchRepository.findOne(id);
		}
		return tournament;
	}
	
	@Override
	public List<Tournament> findByName(String name) {
		return repository.findByNameContaining(name);
	}
}
