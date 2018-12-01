package OutputMapper;

import java.sql.SQLException;

import pojo.DiscardCard;
import tdg.DiscardCardTDG;

public abstract class DiscardCardOuputMapper {
	public static void insert(DiscardCard c) throws SQLException {
		DiscardCardTDG.insert(c.getId(), c.getVersion(), c.getGameId(), c.getPlayerId(), c.getCardId());
	}
}
