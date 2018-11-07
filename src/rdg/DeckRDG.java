package rdg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DbRegistry;
import pojo.Card;

public class DeckRDG {
	
	private long id;
	private long ownerId;
	private List<Card> cards;
	
	public DeckRDG(long id, long ownerId, List<Card> cards) {
		super();
		this.id = id;
		this.ownerId = ownerId;
		this.cards = cards;
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public long getOwnerId() {
		return ownerId;
	}

	public long getId() {
		return id;
	}
	
	public static DeckRDG findByPlayer(long playerId) throws SQLException{
		DeckRDG result = null;
		Connection connection = new DbRegistry().getConnection();
		String query = "SELECT * FROM deck WHERE owner_id =? ORDER BY id";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setLong(1, playerId);
		ResultSet rs = ps.executeQuery();
		List<Card> cards = new ArrayList<Card>();
		long deckId = -1;
		//Read first line
		if(rs.next()){
			deckId = rs.getInt("id");
			cards.add(new Card(rs.getString("card_type").charAt(0), rs.getString("card_name")));
		}
		//Read all next lines
		while(rs.next() && rs.getInt("id") == deckId){
			cards.add(new Card(rs.getString("card_type").charAt(0), rs.getString("card_name")));
		}
		if(deckId != -1){
			result = new DeckRDG(deckId, playerId, cards);
		}
		ps.close();
		connection.close();
		
		return result;
	}
	public void insert(){
		
	}
}
