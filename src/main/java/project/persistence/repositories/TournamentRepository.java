package project.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import project.persistence.entities.Tournament;

public interface TournamentRepository extends JpaRepository<Tournament, Long>{

	List<Tournament> findByNameContaining(String name);
}
