package command;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import InputMapper.UserInputMapper;
import pojo.User;

public class ListPlayerCommand implements ICommand {

	@Override
	public void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, Exception {
		long userId = (Long)request.getSession(true).getAttribute("userid");
		List<User> players = new ArrayList<User>();
		players = UserInputMapper.findAll();
		
		request.setAttribute("players", players);

	}

}
