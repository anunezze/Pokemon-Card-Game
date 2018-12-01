package finder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DbRegistry;

public abstract class DeckFinder {
	public static ResultSet findAllByOwner(long ownerId) throws SQLException {
		String query = "SELECT DISTINCT id, version, owner_id FROM deck WHERE owner_id =?";
		PreparedStatement ps = DbRegistry.getConnection().prepareStatement(query);
		ps.setLong(1, ownerId);
				
		return ps.executeQuery();
	}
	public static ResultSet findDeckById(long findId) throws SQLException {
		String query = "SELECT * FROM deck WHERE id =? ORDER BY card_id";
		PreparedStatement ps = DbRegistry.getConnection().prepareStatement(query);
		ps.setLong(1, findId);
				
		return ps.executeQuery();
	}
	
	public static ResultSet findCard(long cardId) throws SQLException {
		String query = "SELECT * FROM deck WHERE card_id =?;";
		PreparedStatement ps = DbRegistry.getConnection().prepareStatement(query);
		ps.setLong(1, cardId);
				
		return ps.executeQuery();
	}
}
