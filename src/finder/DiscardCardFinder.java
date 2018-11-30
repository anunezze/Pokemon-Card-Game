package finder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DbRegistry;

public abstract class DiscardCardFinder {
	public static ResultSet find(long gameId, long playerId) throws SQLException{
		String query = "SELECT * FROM discard_card WHERE game_id =? AND player_id=?;";
		PreparedStatement ps = DbRegistry.getConnection().prepareStatement(query);
		ps.setLong(1, gameId);
		ps.setLong(2, playerId);
		return ps.executeQuery();
	}
}
