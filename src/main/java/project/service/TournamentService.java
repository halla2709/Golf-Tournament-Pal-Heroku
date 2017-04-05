package project.service;

import java.util.List;

import project.persistence.entities.Tournament;

public interface TournamentService {
	
    /**
     * Get all {@link MatchPlayTournament}s
     * @return A list of {@link MatchPlayTournament}s
     */
    List<Tournament> findAll();

    /**
     * Find a {@link MatchPlayTournament} based on {@link Long id}
     * @param id {@link Long}
     * @return A {@link MatchPlayTournament} with {@link Long id}
     */
    Tournament findOne(Long id);
    
    List<Tournament> findByName(String name);

    List<Tournament> findByGolfer(Long golferSocial);
}

