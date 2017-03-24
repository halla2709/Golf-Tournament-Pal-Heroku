package project.service;

import java.util.List;

import project.persistence.entities.Golfer;
import project.persistence.entities.UserInfo;

public interface GolferService {
	
	Golfer save(Golfer player);

	List<Golfer> findAll();
	
	Golfer delete(Golfer player);
	
	UserInfo save(UserInfo user);
	
	UserInfo findOneUser(long social);
	
	Golfer addFriendForGolfer(Golfer golfer, Golfer friend);
	
	Golfer findOne(long social);
	
	boolean areFriends(Golfer golfer, Golfer friend);
}	

