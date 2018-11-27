package pojo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import InputMapper.DeckInputMapper;

public class DeckProxy implements IDeck{
	private Deck deck;
	
	public DeckProxy(long id, int version, long ownerId) {
		deck = new Deck(id, version, ownerId, new ArrayList<Card>());
	}

	@Override
	public long getOwnerId() {
		return deck.getOwnerId();
	}

	@Override
	public List<Card> getCards() throws SQLException, Exception {
		return DeckInputMapper.findById(deck.getOwnerId()).getCards();
	}
	
}
