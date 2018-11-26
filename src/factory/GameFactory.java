package factory;

import pojo.Game;
import util.IdGenerator;

public class GameFactory {
	public static Game createNew(
			long player1, 
			long player2, 
			long currentPlayer, 
			long deck1, 
			long deck2, 
			String p1Status, 
			String p2Status) {
		Game g = new Game(
				IdGenerator.getInstance().createID(), 
				1, 
				player1, 
				player2, 
				currentPlayer, 
				deck1, 
				deck2, 
				p1Status, 
				p2Status);
		g.markNew();
		return g;
	}
	public static Game createClean(
			long id, 
			int version, 
			long player1, 
			long player2, 
			long currentPlayer, 
			long deck1, 
			long deck2, 
			String p1Status, 
			String p2Status) {
		Game g = new Game(
				id, 
				version, 
				player1, 
				player2, 
				currentPlayer, 
				deck1, 
				deck2, 
				p1Status, 
				p2Status);
		g.markClean();
		return g;
		
	}
}
