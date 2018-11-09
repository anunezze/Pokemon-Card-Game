package rdg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DbRegistry;
import util.IdGenerator;

public class HandRDG {
	
	private long id;
	private long gameId;
	private long playerId;
	private int handSize;
	private int deckSize;
	private int discardSize;
	private long benchId;
	
	public HandRDG(long id, long gameId, long playerId, int handSize, int deckSize, int discardSize, long benchId) {
		super();
		this.id = id;
		this.gameId = gameId;
		this.playerId = playerId;
		this.handSize = handSize;
		this.deckSize = deckSize;
		this.discardSize = discardSize;
		this.benchId = benchId;
	}
	public long getId() {
		return id;
	}
	public long getGameId() {
		return gameId;
	}
	public long getPlayerId() {
		return playerId;
	}
	public int getHandSize() {
		return handSize;
	}
	public int getDeckSize() {
		return deckSize;
	}
	public int getDiscardSize() {
		return discardSize;
	}
	public long getBenchId() {
		return benchId;
	}
	
	public static HandRDG find(long gameId, long playerId) throws SQLException {
		HandRDG result = null;
		Connection connection = new DbRegistry().getConnection();
		String query = "SELECT * FROM hand WHERE game_id = ? AND player_id=?";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setLong(1, gameId);
		ps.setLong(2, playerId);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			result = new HandRDG(rs.getInt("id"),
					rs.getInt("game_id"), 
					rs.getInt("player_id"), 
					rs.getInt("hand_size"),
					rs.getInt("deck_size"),
					rs.getInt("discard_size"),
					rs.getInt("bench_id"));
		}
		return result;
	}
	
	public void insert() throws SQLException {
		Connection connection = new DbRegistry().getConnection();
		String query = "INSERT INTO hand (id,game_id,player_id, hand_size, deck_size, discard_size, bench_id) VALUES (?,?,?,?,?,?,?);";
		PreparedStatement ps = connection.prepareStatement(query);
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
