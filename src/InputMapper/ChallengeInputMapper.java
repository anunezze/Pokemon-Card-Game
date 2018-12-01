package InputMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import factory.ChallengeFactory;
import finder.ChallengeFinder;
import pojo.Challenge;
import util.ChallengeStatus;

public abstract class ChallengeInputMapper {
	public static Challenge findById(long id) throws SQLException {
		ResultSet rs = ChallengeFinder.find(id);
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
		ResultSet rs = ChallengeFinder.findAll();
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
