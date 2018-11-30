package pojo;

import java.util.ArrayList;
import java.util.List;

import util.IdGenerator;

public class Deck extends DomainObject implements IDeck, IDomainObject{
	private long ownerId;
	private List<Card> cards;
	
	public Deck(long id, int version, long ownerId, List<Card> cards) {
		super(id, version);
		this.ownerId = ownerId;
		this.cards = cards;
	}
	
	public Deck(long id, int version, long ownerId, String cards){
		super(id, version);
		this.ownerId = ownerId;
		List<Card> c = new ArrayList<Card>();
		String[] cardsArray = cards.split("\n");
		for(int i = 0; i< cardsArray.length; i++){
			String[] line = cardsArray[i].split(" ");
			System.out.println("----------------");
			for(int j = 0; j<line.length; j++) {
				System.out.println(line[j] + " line:" + j);
			}
			c.add(new Card(IdGenerator.getInstance().createID(), line[0].charAt(0), line[1].substring(1, line[1].length()-1)));
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
