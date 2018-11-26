package pojo;

public class Hand extends DomainObject {
	private long gameId;
	private long playerId;
	private int handSize;
	private int deckSize;
	private int discardSize;
	private long benchId;
	
	public Hand(long id, int version, long gameId, long playerId, int handSize, int deckSize, int discardSize, long benchId) {
		super(id, version);
		this.gameId = gameId;
		this.playerId = playerId;
		this.handSize = handSize;
		this.deckSize = deckSize;
		this.discardSize = discardSize;
		this.benchId = benchId;
	}

	public int getHandSize() {
		return handSize;
	}

	public void setHandSize(int handSize) {
		this.handSize = handSize;
	}

	public int getDeckSize() {
		return deckSize;
	}

	public void setDeckSize(int deckSize) {
		this.deckSize = deckSize;
	}

	public int getDiscardSize() {
		return discardSize;
	}

	public void setDiscardSize(int discardSize) {
		this.discardSize = discardSize;
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
	
	
	
	
	
	
}
