package project.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import project.persistence.entities.Golfer;


public interface GolferRepository extends JpaRepository<Golfer, Long>{
	
	Golfer save(Golfer player);

	List<Golfer> findAll();
	
	Golfer findOne(Long social);
	
}
