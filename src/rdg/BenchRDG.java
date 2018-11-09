package rdg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import database.DbRegistry;
import pojo.Card;
import util.IdGenerator;

public class BenchRDG {
	
	private long id;
	private long handId;
	private List<Card> cards;
	
	public BenchRDG(long id, long handId, List<Card> cards) {
		super();
		this.id = id;
		this.handId = handId;
		this.cards = cards;
	}
	public long getId() {
		return id;
	}
	public long getHandId() {
		return handId;
	}
	public List<Card> getCards() {
		return cards;
	}
	
	public void insert() throws SQLException {
//		Connection connection = new DbRegistry().getConnection();
//		String query = "INSERT INTO hand (id,hand_id, card_id) VALUES (?,?,?);";
//		PreparedStatement ps = connection.prepareStatement(query);
//		ps.setLong(1,IdGenerator.getInstance().createID());
//		ps.setLong(2, this.handId);
//		ps.setLong(3, this.cardId);
//		ps.executeUpdate();
//		ps.close();
//		connection.close();
	}
}
