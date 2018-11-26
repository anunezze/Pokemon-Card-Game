package factory;

import java.util.List;

import pojo.Card;
import pojo.Deck;

public class DeckFactory {
	public static Deck createNew(long id, int version, long ownerId, List<Card> cards) throws Exception {
		Deck d = new Deck(id,version,ownerId,cards);
		d.markNew();
		return d;
	}
	public static Deck createNew(long id, int version, long ownerId, String cards) throws Exception {
		Deck d = new Deck(id,version,ownerId,cards);
		d.markNew();
		return d;
	}
	
	public static Deck createClean(long id, int version, long ownerId, List<Card> cards) throws Exception {
		Deck d = new Deck(id,version,ownerId,cards);
		d.markClean();
		return d;
	}
	public static Deck createClean(long id, int version, long ownerId, String cards) throws Exception {
		Deck d = new Deck(id,version,ownerId,cards);
		d.markClean();
		return d;
	}
}
