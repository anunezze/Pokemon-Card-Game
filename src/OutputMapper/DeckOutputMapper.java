package OutputMapper;

import java.sql.SQLException;
import java.util.List;

import pojo.Card;
import pojo.Deck;
import tdg.DeckTDG;

public abstract class DeckOutputMapper {
	public static void insert(Deck deck) throws SQLException {
		List<Card> cards = deck.getCards();
		for(Card c : cards) {
			DeckTDG.insert(deck.getId(), deck.getVersion(), deck.getOwnerId(), c.getType(), c.getName(), c.getId());
		}
	}
}
