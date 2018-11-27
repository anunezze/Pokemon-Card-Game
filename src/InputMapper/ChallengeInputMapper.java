package InputMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import factory.ChallengeFactory;
import pojo.Challenge;
import tdg.ChallengeTDG;
import util.ChallengeStatus;

public abstract class ChallengeInputMapper {
	public static Challenge findById(long id) throws SQLException {
		ResultSet rs = ChallengeTDG.find(id);
		Challenge c = null;
		if(rs.next()) {
			c = ChallengeFactory.createClean(
					rs.getInt("id"), 
					rs.getInt("version"),
					rs.getInt("challenger"),
					rs.getInt("challengee"),
					rs.getInt("challenger_deck"),
					ChallengeStatus.valueOf(rs.getInt("status")));
		}
		return c;
	}
	public static List<Challenge> findAll() throws SQLException{
		ResultSet rs = ChallengeTDG.findAll();
		List<Challenge> result = new ArrayList<Challenge>();
		while(rs.next()) {
			result.add(ChallengeFactory.createClean(
					rs.getInt("id"), 
					rs.getInt("version"), 
					rs.getInt("challenger"), 
					rs.getInt("challengee"), 
					rs.getInt("challenger_deck"), 
					ChallengeStatus.valueOf(rs.getInt("status"))));
		}
		return result;
	}
}
