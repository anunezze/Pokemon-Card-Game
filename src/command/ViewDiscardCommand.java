package command;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import InputMapper.DiscardCardInputMapper;
import pojo.DiscardCard;

public class ViewDiscardCommand implements ICommand {

	@Override
	public void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, Exception {
		long myId = (Long)request.getSession().getAttribute("userid");
		long gameId = (Long)(request.getAttribute("game"));
		long playerId = (Long)(request.getAttribute("player"));
		List<DiscardCard> discardPile = DiscardCardInputMapper.find(gameId, playerId);
		request.setAttribute("pile", discardPile);
	}
}
