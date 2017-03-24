package project.persistence.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import project.persistence.entities.MatchPlayTournament;

public interface MatchPlayCreatorRepository extends JpaRepository<MatchPlayTournament, Long>{
	
	MatchPlayTournament save(MatchPlayTournament headontournament);
	
	void delete(MatchPlayTournament headontournament);

}
