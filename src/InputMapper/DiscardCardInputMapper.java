package InputMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import factory.DiscardCardFactory;
import finder.DiscardCardFinder;
import pojo.DiscardCard;

public abstract class DiscardCardInputMapper {
	public static List<DiscardCard> find(long gameId, long playerId) throws SQLException {
		List<DiscardCard> result = new ArrayList<DiscardCard>();
		ResultSet rs = DiscardCardFinder.find(gameId, playerId);
		while(rs.next()) {
			result.add(DiscardCardFactory.createClean(
					rs.getLong("id"),
					rs.getInt("version"), 
					rs.getLong("game_id"), 
					rs.getLong("player_id"), 
					rs.getLong("cardId")));
		}
		return result;
	}
}
