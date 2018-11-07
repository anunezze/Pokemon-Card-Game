package rdg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.IdGenerator;

import com.mysql.jdbc.Statement;

import database.DbRegistry;
import pojo.Card;

public class DeckRDG {
	
	private long id;
	private long ownerId;
	private List<Card> cards;
	
	public DeckRDG(long id, long ownerId, List<Card> cards) throws Exception {
		super();
		this.id = id;
		this.ownerId = ownerId;
		this.cards = cards;
		if(cards.size()>40){
			throw new Exception("Too many cards.");
		}
		else if(cards.size()<40){
			throw new Exception("Too few cards.");
		}
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
	
	public static DeckRDG findByPlayer(long playerId) throws Exception{
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
	public void insert() throws SQLException{
		long deckId = IdGenerator.createID();
		Connection connection = new DbRegistry().getConnection();
		for(Card c : this.cards){
			String query = "INSERT INTO deck (id,owner_id,card_type, card_name) VALUES (?,?,?,?);";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setLong(1,deckId);
			ps.setLong(2,this.ownerId);
			ps.setString(3,String.valueOf(c.getType()));
			ps.setString(4, c.getName());
			ps.executeUpdate();
			ps.close();
		}
		connection.close();
	}
}
