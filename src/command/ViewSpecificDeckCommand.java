package command;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import InputMapper.DeckInputMapper;
import pojo.Deck;
import pojo.IDeck;

public class ViewSpecificDeckCommand implements ICommand {

	@Override
	public void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, Exception {
		long id = (Long)request.getAttribute("deckId");
		Deck deck = DeckInputMapper.findById(id);
		if(deck == null) {
			throw new Exception("Deck #" + id + " doesn't exists");
		}
		request.setAttribute("cards", deck.getCards());
	}

}
