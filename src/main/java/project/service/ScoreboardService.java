package project.service;

import java.util.Date;
import java.util.List;

import project.persistence.entities.Golfer;
import project.persistence.entities.Round;
import project.persistence.entities.ScoreboardTournament;

public interface ScoreboardService {
	
	ScoreboardTournament save(List<Golfer> players, int numberOfRounds, String course, String name, Date startDate);
	
	void delete(ScoreboardTournament scoreboardtournament);
	 /**
     * Save a {@link ScoreboardTournament}
     * @param matchPlay {@link ScoreboardTournament} to be saved
     * @return {@link ScoreboardTournament} that was saved
     */

    /**
     * Get all {@link ScoreboardTournament}s
     * @return A list of {@link ScoreboardTournament}s
     */
    List<ScoreboardTournament> findAll();


    /**
     * Find a {@link ScoreboardTournament} based on {@link Long id}
     * @param id {@link Long}
     * @return A {@link ScoreboardTournament} with {@link Long id}
     */
    ScoreboardTournament findOne(Long id);

	Round getRound(Long id, long social, int round);

	ScoreboardTournament addRound(Long id, long social, int round, int[] scores);

}

