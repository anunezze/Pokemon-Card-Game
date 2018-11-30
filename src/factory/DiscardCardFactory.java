package factory;

import pojo.DiscardCard;
import util.IdGenerator;

public abstract class DiscardCardFactory {
	public static DiscardCard createNew(long gameId, long playerId, long cardId) {
		DiscardCard c = new DiscardCard(IdGenerator.getInstance().createID(), 1, gameId, playerId, cardId );
		c.markNew();
		return c;
		
	}
	public static DiscardCard createClean(long id, int version, long gameId, long playerId, long cardId) {
		DiscardCard c = new DiscardCard(id, version, gameId, playerId, cardId );
		c.markClean();
		return c;
	}
}
