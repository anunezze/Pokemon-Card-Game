package InputMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import factory.HandFactory;
import pojo.Hand;
import tdg.HandTDG;

public abstract class HandInputMapper {
	public static Hand find(long gameId, long playerId) throws SQLException {
		ResultSet rs = HandTDG.find(gameId, playerId);
		Hand h = null;
		if(rs.next()) {
			h= HandFactory.createClean(
					rs.getInt("id"), 
					rs.getInt("version"), 
					rs.getInt("game_id"), 
					rs.getInt("player_id"), 
					rs.getInt("hand_size"), 
					rs.getInt("deck_size"), 
					rs.getInt("discard_size"), 
					rs.getInt("bench_id"),
					rs.getInt("bench_size"));
		}
		return h;
	}
}
