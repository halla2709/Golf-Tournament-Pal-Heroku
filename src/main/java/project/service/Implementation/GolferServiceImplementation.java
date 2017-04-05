package project.service.Implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.persistence.entities.Golfer;
import project.persistence.entities.UserInfo;
import project.persistence.repositories.GolferRepository;
import project.persistence.repositories.UserRepository;
import project.service.GolferService;

@Service
public class GolferServiceImplementation implements GolferService{

	private UserRepository uRepository;
	private GolferRepository repository;
	
	@Autowired
	public GolferServiceImplementation(GolferRepository repository, UserRepository uRepository) {
		this.repository = repository;
		this.uRepository = uRepository;
	}
	
	@Override
	public Golfer save(Golfer player) {
		return repository.save(player);
	}
	
	@Override
	public List<Golfer> findAll() {
		return repository.findAll();
	}
	
	@Override
	public Golfer delete(Golfer player) {
		return null;
	}
	
	public UserInfo save(UserInfo user) {
		return uRepository.save(user);
	}
	
	public UserInfo findOneUser(long social) {
		return uRepository.findOne(social);
	}
	
	public Golfer addFriendForGolfer(Golfer golfer, Golfer friend) {
		golfer.addFriend(friend);
		return save(golfer);
	}
	
	public boolean areFriends(Golfer golfer, Golfer friend) {
		List<Golfer> friends = golfer.getFriends();
		for(int i = 0; i < friends.size(); i++) {
			if(friends.get(i).getSocial() == friend.getSocial())
				return true;
		}
		return false;
	}
	
	public Golfer findOne(long social) {
		return repository.findOne(social);
	}
	
	public void addParticipantsFriendsForGolfer(Golfer host, List<Golfer> participants) {
		int playerNum = participants.size();
		for(int i = 0; i < playerNum; i++) {
			Golfer golfer = participants.get(i);
			if(host.getSocial() != golfer.getSocial()) {
				Golfer dataGolfer = findOne(golfer.getSocial());
				if(dataGolfer != null) {
					golfer = dataGolfer;
					if(!areFriends(host, golfer)) {
						addFriendForGolfer(host, golfer);
					}
				}
				else {
					save(golfer);
					addFriendForGolfer(host, golfer);
				}
			}		
			
		}
	}

}
