package project.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import project.persistence.entities.UserInfo;

public interface UserRepository extends JpaRepository<UserInfo, Long> {
	
}
