package OutputMapper;

import java.sql.SQLException;

import pojo.Game;
import tdg.GameTDG;

public abstract class GameOutputMapper {
	public static void insert(Game g) throws SQLException {
		GameTDG.insert(
				g.getId(),
				g.getVersion(),
				g.getPlayer1(), 
				g.getPlayer2(),
				g.getCurrentPlayer(),
				g.getDeck1(),
				g.getDeck2(),
				g.getP1Status(),
				g.getP2Status());
	}
}
