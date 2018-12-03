package command;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import InputMapper.DiscardCardInputMapper;
import InputMapper.GameInputMapper;
import pojo.DiscardCard;
import pojo.Game;

public class ViewDiscardCommand implements ICommand {

	@Override
	public void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, Exception {
		long myId = (Long)request.getSession().getAttribute("userid");
		long gameId = (Long)(request.getAttribute("game"));
		long playerId = (Long)(request.getAttribute("player"));
		Game game = GameInputMapper.findById(gameId);
		if(game.getPlayer1() != playerId && game.getPlayer2() != playerId) {
			throw new Exception("This player is not in this game");
		}
		if(game.getPlayer1() != myId && game.getPlayer2() != myId) {
			throw new Exception("This is not my game");
		}
		List<DiscardCard> discardPile = DiscardCardInputMapper.find(gameId, playerId);
		request.setAttribute("pile", discardPile);
	}
}
