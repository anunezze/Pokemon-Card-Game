package pojo;

import java.util.ArrayList;
import java.util.List;

public class Hand extends DomainObject {
	private long gameId;
	private long playerId;
	private int handSize;
	private int deckSize;
	private int discardSize;
	private long benchId;
	private int benchSize;
	
	public Hand(long id, int version, long gameId, long playerId, int handSize, int deckSize, int discardSize, long benchId, int benchSize) {
		super(id, version);
		this.gameId = gameId;
		this.playerId = playerId;
		this.handSize = handSize;
		this.deckSize = deckSize;
		this.discardSize = discardSize;
		this.benchId = benchId;
		this.benchSize = benchSize;
	}

	public int getHandSize() {
		return handSize;
	}

	public void setHandSize(int handSize) {
		this.handSize = handSize;
		this.markDirty();
	}

	public int getDeckSize() {
		return deckSize;
	}

	public void setDeckSize(int deckSize) {
		this.deckSize = deckSize;
		this.markDirty();
	}

	public int getDiscardSize() {
		return discardSize;
	}

	public void setDiscardSize(int discardSize) {
		this.discardSize = discardSize;
		this.markDirty();
	}

	public long getGameId() {
		return gameId;
	}

	public long getPlayerId() {
		return playerId;
	}

	public long getBenchId() {
		return benchId;
	}
	
	public int getBenchSize() {
		return benchSize;
	}

	public void setBenchSize(int benchSize) {
		this.benchSize = benchSize;
	}

	public List<Card> getCurrentHand(Deck deck, List<BenchPokemon> benchCards){
		List<Card> currentHand = new ArrayList<Card>();
		int counter = 0;
		boolean foundInBench = false;
		while(currentHand.size() <= this.handSize) { 
			for(int i = 0; i < benchCards.size(); i++) {
				long currentCardId = deck.getCards().get(counter).getId();
				if(currentCardId == benchCards.get(i).getPokemonId()) {
					foundInBench = true;
				}
				for(int j = 0; !foundInBench && j < benchCards.get(i).getEnergies().size(); j++) {
					long energyId = benchCards.get(i).getEnergies().get(j);
					if(currentCardId == energyId) {
						foundInBench = true;
					}
				}
			}
			if(!foundInBench && handSize != 0) {
				currentHand.add(deck.getCards().get(counter));
			}
			counter++;
		}
		
		return currentHand;
	}
}
