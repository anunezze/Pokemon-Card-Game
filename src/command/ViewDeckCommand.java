package command;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import InputMapper.DeckInputMapper;
import database.DbRegistry;
import pojo.IDeck;

public class ViewDeckCommand implements ICommand {

	@Override
	public void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, Exception {
		long myId = (Long)request.getSession(true).getAttribute("userid");
		List<IDeck> decks = DeckInputMapper.findAllByOwner(myId);

		request.setAttribute("decks", decks);
	}

}
