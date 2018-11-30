package command;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import InputMapper.GameInputMapper;
import InputMapper.HandInputMapper;
import pojo.Game;
import pojo.Hand;

public class DrawCardCommand implements ICommand {

	@Override
	public void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, Exception {
		long myId = (Long)(request.getAttribute("userid"));
		long gameId = (Long)(request.getAttribute("game"));
		Game game = GameInputMapper.findById(gameId);
		if(game == null) {
			throw new Exception("Game was not found");
		}
		if(myId != game.getPlayer1() && myId != game.getPlayer2()) {
			throw new Exception("This is not your game");
		}
		Hand hand = HandInputMapper.find(game.getId(), myId);
		hand.setDeckSize(hand.getDeckSize()-1);
		hand.setHandSize(hand.getHandSize() + 1);
		game.markDirty();

	}

}
