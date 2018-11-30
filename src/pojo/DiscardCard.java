package pojo;

public class DiscardCard extends DomainObject{
	private long gameId;
	private long playerId;
	private long cardId;
	
	public DiscardCard(long id, int version, long gameId, long playerId, long cardId) {
		super(id, version);
		this.gameId = gameId;
		this.playerId = playerId;
		this.cardId = cardId;
	}

	public long getGameId() {
		return gameId;
	}

	public long getPlayerId() {
		return playerId;
	}

	public long getCardId() {
		return cardId;
	}	
}
