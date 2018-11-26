package OutputMapper;

import pojo.Hand;
import tdg.HandTDG;

public abstract class HandOutputMapper {
	public static void insert(Hand h) {
		HandTDG.insert(h.getId(),
				h.getVersion(),
				h.getGameId(),
				h.getPlayerId(),
				h.getHandSize(),
				h.getDeckSize(),
				h.getDiscardSize(),
				h.getBenchId());
	}
}
