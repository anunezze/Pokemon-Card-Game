package pojo;

import java.util.ArrayList;
import java.util.List;

import util.IdGenerator;

public class Deck extends DomainObject implements IDeck, IDomainObject{
	private long ownerId;
	private List<Card> cards;
	
	public Deck(long id, int version, long ownerId, List<Card> cards) throws Exception {
		super(id, version);
		this.ownerId = ownerId;
		this.cards = cards;
		if(cards.size()>40){
			throw new Exception("Too many cards in deck of user #" + this.ownerId);
		}
		else if(cards.size()<40){
			throw new Exception("Too few cards in deck of user #" + this.ownerId);
		}
	}
	
	public Deck(long id, int version, long ownerId, String cards) throws Exception{
		super(id, version);
		this.ownerId = ownerId;
		List<Card> c = new ArrayList<Card>();
		String[] cardsArray = cards.split("\n");
		for(int i = 0; i< cardsArray.length; i++){
			String[] line = cardsArray[i].split(" ");
			c.add(new Card(IdGenerator.getInstance().createID(), line[0].charAt(0), line[1].substring(1, line[1].length()-1)));
		}
		if(c.size()>40){
			throw new Exception("Too many cards in deck of user #" + this.ownerId);
		}
		else if(c.size()<40){
			throw new Exception("Too few cards in deck of user #" + this.ownerId);
		}
		this.cards = c;
	}

	public long getOwnerId() {
		return ownerId;
	}

	public List<Card> getCards() {
		return cards;
	}
	
}
