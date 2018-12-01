package command;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import factory.DeckFactory;
import pojo.Deck;
import util.IdGenerator;

public class UploadDeckCommand implements ICommand {

	@Override
	public void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, Exception {
		long userId = (Long)request.getSession(true).getAttribute("userid");
		
		String deckParameter = request.getParameter("deck");
		Deck deck = DeckFactory.createNew(IdGenerator.getInstance().createID(),1, userId, deckParameter);
		request.getServletContext().log(deck.getCards().size() + " is my size");
		if(deck.getCards().size()>40){
			throw new Exception("Too many cards in deck of user #" + deck.getOwnerId());
		}
		else if(deck.getCards().size()<40){
			throw new Exception("Too few cards in deck of user #" + deck.getOwnerId());
		}
		request.setAttribute("message", "Upload was done successfully");

	}
	private String uploadDeckForTest() {
		return 
				"e \"Fire\"\n" +
				"e \"Fire\"\n" +
				"p \"Charizard\"\n" +
				"e \"Fire\"\n" +
				"e \"Fire\"\n" +
				"e \"Fire\"\n" +
				"p \"Charizard\"\n" +
				"p \"Meowth\"\n" +
				"e \"Fire\"\n" +
				"t \"Misty\"\n" +
				"t \"Misty\"\n" +
				"e \"Fire\"\n" +
				"e \"Fire\"\n" +
				"e \"Fire\"\n" +
				"p \"Charizard\"\n" +
				"e \"Fire\"\n" +
				"e \"Fire\"\n" +
				"e \"Fire\"\n" +
				"p \"Charizard\"\n" +
				"p \"Meowth\"\n" +
				"e \"Fire\"\n" +
				"t \"Misty\"\n" +
				"t \"Misty\"\n" +
				"e \"Fire\"\n" +
				"e \"Fire\"\n" +
				"e \"Fire\"\n" +
				"p \"Raichu\" \"Pikachu\"\n" +
				"e \"Fire\"\n" +
				"e \"Fire\"\n" +
				"e \"Fire\"\n" +
				"p \"Charizard\"\n" +
				"p \"Meowth\"\n" +
				"e \"Fire\"\n" +
				"t \"Misty\"\n" +
				"t \"Misty\"\n" +
				"e \"Fire\"\n" +
				"e \"Fire\"\n" +
				"e \"Fire\"\n" +
				"p \"Charizard\"\n" +
				"e \"Fire\"\n";
	}

}
