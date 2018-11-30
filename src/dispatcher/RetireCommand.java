package dispatcher;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import InputMapper.GameInputMapper;
import command.ICommand;
import pojo.Game;

public class RetireCommand implements ICommand {

	@Override
	public void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, Exception {
		long myId = (Long)request.getSession().getAttribute("userid"); 
		long gameId = (Long)(request.getAttribute("game"));
		Game game = GameInputMapper.findById(gameId);
		if(game==null) {
			throw new Exception("Game was not found");
		}
		
		if(myId == game.getPlayer1()) {
			game.setP1Status("retired");
		}
		else if(myId == game.getPlayer2()) {
			game.setP2Status("retired");
		}
		else {
			throw new Exception("This is not your game.");
		}
		request.setAttribute("message", "User '" + myId + "' retired successfully from game " + game.getId());
	}
}
