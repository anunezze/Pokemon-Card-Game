package OutputMapper;

import java.sql.SQLException;

import pojo.Challenge;
import tdg.ChallengeTDG;
import util.LostUpdateException;

public abstract class ChallengeOutputMapper {
	public static void insert(Challenge challenge) throws SQLException {
		ChallengeTDG.insert(challenge.getId(), 
							challenge.getVersion(), 
							challenge.getChallenger(), 
							challenge.getChallengee(),
							challenge.getChallengerDeck(),
							challenge.getStatus().ordinal());
	}
	
	public static void update(Challenge challenge) throws SQLException, LostUpdateException {
		int result =ChallengeTDG.update(
				challenge.getId(), 
				challenge.getVersion(), 
				challenge.getChallenger(), 
				challenge.getChallengee(), 
				challenge.getChallengerDeck(), 
				challenge.getStatus().ordinal());
		if(result == 0) {
			throw new LostUpdateException("This challenge version is not up to date with Db.");
		}
	}
}
