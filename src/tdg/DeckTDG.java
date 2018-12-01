package tdg;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DbRegistry;

public abstract class DeckTDG {
	public static void insert(long id, int version, long ownerId, char cardType, String cardName, long cardId, String base) throws SQLException {
		String query = "INSERT INTO deck (id, version, owner_id, card_type, card_name, card_id, base) VALUES (?,?,?,?,?,?,?);";
		PreparedStatement ps = DbRegistry.getConnection().prepareStatement(query);
		ps.setLong(1, id);
		ps.setInt(2, version);
		ps.setLong(3, ownerId);
		ps.setString(4, String.valueOf(cardType));
		ps.setString(5, cardName);
		ps.setLong(6, cardId);
		ps.setString(7, base);
		ps.executeUpdate();
	}
	
//	public static ResultSet findAllByOwner(long ownerId) throws SQLException {
//		String query = "SELECT DISTINCT id, version, owner_id FROM deck WHERE owner_id =?";
//		PreparedStatement ps = DbRegistry.getConnection().prepareStatement(query);
//		ps.setLong(1, ownerId);
//				
//		return ps.executeQuery();
//	}
//	public static ResultSet findDeckById(long findId) throws SQLException {
//		String query = "SELECT * FROM deck WHERE id =? ORDER BY card_id";
//		PreparedStatement ps = DbRegistry.getConnection().prepareStatement(query);
//		ps.setLong(1, findId);
//				
//		return ps.executeQuery();
//	}
}
