package tdg;

import java.sql.PreparedStatement;

import database.DbRegistry;
import util.IdGenerator;

public abstract class HandTDG {
	public static void insert(long id,int version, long gameId, long playerId, int handSize, int deckSize, int discardSize, long benchId) {
		
		String query = "INSERT INTO hand (id,version,game_id,player_id, hand_size, deck_size, discard_size, bench_id) VALUES (?,?,?,?,?,?,?);";
		PreparedStatement ps = DbRegistry.getConnection().prepareStatement(query);
		ps.setLong(1,IdGenerator.getInstance().createID());
		ps.setLong(2, this.gameId);
		ps.setLong(3, this.playerId);
		ps.setInt(4, this.handSize);
		ps.setInt(5, this.deckSize);
		ps.setInt(6, this.discardSize);
		ps.setLong(7, this.benchId);
		ps.executeUpdate();
		ps.close();
		connection.close();
	}
}
