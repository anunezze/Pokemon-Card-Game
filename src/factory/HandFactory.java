package factory;

import pojo.Hand;
import util.IdGenerator;

public class HandFactory {
	public static Hand createClean(long id, int version, long gameId, long playerId, int handSize, int deckSize, int discardSize, long benchId) {
		Hand h = new Hand(id,version,gameId,playerId,handSize,deckSize,discardSize, benchId);
		h.markClean();
		return h;
	}
	public static Hand createNew(long gameId, long playerId, int deckSize) {
		Hand h = new Hand(
				IdGenerator.getInstance().createID(),
				1,
				gameId,
				playerId,
				0,
				deckSize,
				0,
				IdGenerator.getInstance().createID());
		h.markNew();
		return h;
	}
}
