package project.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import project.persistence.entities.Tournament;

public interface TournamentRepository extends JpaRepository<Tournament, Long>{

	List<Tournament> findByNameContaining(String name);
	
	@Query("select t from Tournament t inner join t.players p where p.social = :golferSocial")
	List<Tournament> findByGolfer(@Param("golferSocial") Long golferSocial);
}
