package rdg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DbRegistry;

public class GameRDG {
	
	private long id;
	private long player1;
	private long player2;
	private long deck1;
	private long deck2;
	
	public GameRDG(long id, long player1, long player2, long deck1, long deck2) {
		super();
		this.id = id;
		this.player1 = player1;
		this.player2 = player2;
		this.deck1 = deck1;
		this.deck2 = deck2;
	}

	public long getId() {
		return id;
	}

	public long getPlayer1() {
		return player1;
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
	
	public void insert() throws SQLException{
		Connection connection = new DbRegistry().getConnection();
		String query = "INSERT INTO game (player1,player2, deck1, deck2) VALUES (?,?,?,?);";
		PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		ps.setLong(1, this.player1);
		ps.setLong(2, this.player2);
		ps.setLong(3, this.deck1);
		ps.setLong(4, this.deck2);
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		if(rs.next()){
			this.id = rs.getInt(1);
		}
		ps.close();
		connection.close();
	}
}
