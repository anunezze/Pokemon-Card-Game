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
		while(currentHand.size() < this.handSize && counter < 40) {
			System.out.println(currentHand.size() + " < " + this.handSize + "is true");
			long currentCardId = deck.getCards().get(counter).getId();
			for(int i = 0; !foundInBench && i < benchCards.size(); i++) {
				System.out.println(currentCardId + " current id card");
				BenchPokemon benchItem = benchCards.get(i);
				System.out.println(benchItem.getPokemonId() + " benchItem pokemon id");
				if(currentCardId == benchItem.getPokemonId()) {
					System.out.println("set to true first");
					foundInBench = true;
				}
				for(int j = 0; !foundInBench && j < benchItem.getEnergies().size(); j++) {
					long energyId = benchItem.getEnergies().get(j);
					if(currentCardId == energyId) {
						System.out.println("set to true second");
						foundInBench = true;
					}
				}
			}
			if(!foundInBench) {
				System.out.println("added " + deck.getCards().get(counter).getId());
				currentHand.add(deck.getCards().get(counter));
				
			}
			foundInBench = false;
			counter++;
		}
		
		return currentHand;
	}
}
