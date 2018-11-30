package command;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import InputMapper.GameInputMapper;
import pojo.Game;

public class ListGameCommand implements ICommand {

	@Override
	public void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, Exception {
		long userid = (Long)request.getSession(true).getAttribute("userid");
		List<Game> games = GameInputMapper.findAll();
		request.setAttribute("games", games);
	}

}
