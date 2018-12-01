package InputMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import factory.DeckFactory;
import pojo.Card;
import pojo.Deck;
import pojo.DeckProxy;
import pojo.IDeck;
import tdg.DeckTDG;

public abstract class DeckInputMapper {
	public static List<IDeck> findAllByOwner(long ownerId) throws SQLException, Exception {
		ResultSet rs = DeckTDG.findAllByOwner(ownerId);
		List<IDeck> result = new ArrayList<IDeck>();
		while(rs.next()) {
			result.add(new DeckProxy(rs.getInt("id"),rs.getInt("version"),ownerId));
		}
		
		return result;
	}
	
	public static Deck findById(long id) throws SQLException, Exception{
		ResultSet rs = DeckTDG.findDeckById(id);
		Deck result = null;
		List<Card> cards = new ArrayList<Card>();
		long deckId = -1;
		int version = -1;
		long ownerId = -1;
		
		while(rs.next()) {
			if(cards.isEmpty()) {
				deckId = rs.getInt("id");
				version = rs.getInt("version");
				ownerId = rs.getInt("owner_id");
			}
			cards.add(new Card(rs.getInt("card_id"),rs.getString("card_type").charAt(0), rs.getString("card_name"),rs.getString("base")));
		}
		if(!cards.isEmpty()) {
			result = DeckFactory.createClean(deckId, version, ownerId, cards);
		}
		
		return result;
	}
}
