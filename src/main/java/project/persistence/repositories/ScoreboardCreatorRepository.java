package project.persistence.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import project.persistence.entities.ScoreboardTournament;

public interface ScoreboardCreatorRepository extends JpaRepository<ScoreboardTournament, Long>{
	
	ScoreboardTournament save(ScoreboardTournament scoreboardtournament);
	
	void delete(ScoreboardTournament scoreboardtournament);

}
