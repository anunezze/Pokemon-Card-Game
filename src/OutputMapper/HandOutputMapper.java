package OutputMapper;

import java.sql.SQLException;

import pojo.Hand;
import tdg.HandTDG;

public abstract class HandOutputMapper {
	public static void insert(Hand h) throws SQLException {
		HandTDG.insert(h.getId(),
				h.getVersion(),
				h.getGameId(),
				h.getPlayerId(),
				h.getHandSize(),
				h.getDeckSize(),
				h.getDiscardSize(),
				h.getBenchId(),
				h.getBenchSize());
	}
	public static void update(Hand h) throws SQLException{
		HandTDG.update(h.getId(), h.getVersion(), h.getHandSize(), h.getDeckSize(), h.getDiscardSize(),h.getBenchSize());
	}
}
