package rdg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DbRegistry;
import util.IdGenerator;

public class GameRDG {
	
	private long id;
	private long player1;
	private long player2;
	private long deck1;
	private long deck2;
	private String p1Status;
	private String p2Status;
	
	public GameRDG(long id, long player1, long player2, long deck1, long deck2, String p1Status, String p2Status) {
		super();
		this.id = id;
		this.player1 = player1;
		this.player2 = player2;
		this.deck1 = deck1;
		this.deck2 = deck2;
		this.p1Status = p1Status;
		this.p2Status = p2Status;
	}

	public long getId() {
		return id;
	}

	public long getPlayer1() {
		return player1;
	}

	public String getP1Status() {
		return p1Status;
	}

	public String getP2Status() {
		return p2Status;
	}

	public long getPlayer2() {
		return player2;
	}

	public long getDeck1() {
		return deck1;
	}

	public long getDeck2() {
		return deck2;
	}
	
	public void setP1Status(String p1Status) {
		this.p1Status = p1Status;
	}

	public void setP2Status(String p2Status) {
		this.p2Status = p2Status;
	}

	public void insert() throws SQLException{
		Connection connection = new DbRegistry().getConnection();
		String query = "INSERT INTO game (id, player1,player2, deck1, deck2, p1_status, p2_status) VALUES (?,?,?,?,?,?,?);";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setLong(1, this.id);
		ps.setLong(2, this.player1);
		ps.setLong(3, this.player2);
		ps.setLong(4, this.deck1);
		ps.setLong(5, this.deck2);
		ps.setString(6, this.p1Status);
		ps.setString(7, this.p2Status);
		ps.executeUpdate();
		ps.close();
		connection.close();
	}
	
	public static GameRDG find(long gameId) throws SQLException {
		GameRDG result = null;
		Connection connection = new DbRegistry().getConnection();
		String query = "SELECT * FROM game WHERE id = ?";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setLong(1, gameId);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			result = new GameRDG(rs.getInt("id"), 
						rs.getInt("player1"), 
						rs.getInt("player2"), 
						rs.getInt("deck1"),
						rs.getInt("deck2"),
						rs.getString("p1_status"),
						rs.getString("p2_status"));
		}
		return result;
	}
	
	public static List<GameRDG> findAll() throws SQLException{
		List<GameRDG> result = new ArrayList<GameRDG>();
		Connection connection = new DbRegistry().getConnection();
		String query = "SELECT * FROM game";
		PreparedStatement ps = connection.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			result.add(new GameRDG(rs.getInt("id"),
						rs.getInt("player1"), 
						rs.getInt("player2"), 
						rs.getInt("deck1"), 
						rs.getInt("deck2"),
						rs.getString("p1_status"),
						rs.getString("p2_status")));
		}
		
		return result;
	}
	
	public void update() throws SQLException {
		Connection connection = new DbRegistry().getConnection();
		String query = "UPDATE game SET player1=?,player2=?, deck1=?, deck2=?, p1_status=?, p2_status=? WHERE id=?;";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setLong(1, this.player1);
		ps.setLong(2, this.player2);
		ps.setLong(3, this.deck1);
		ps.setLong(4, this.deck2);
		ps.setString(5, this.p1Status);
		ps.setString(6, this.p2Status);
		ps.setLong(7, this.id);
		ps.executeUpdate();
		ps.close();
		connection.close();
	}
}
