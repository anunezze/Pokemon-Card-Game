package command;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import InputMapper.BenchPokemonInputMapper;
import InputMapper.DeckInputMapper;
import InputMapper.DiscardCardInputMapper;
import InputMapper.GameInputMapper;
import InputMapper.HandInputMapper;
import factory.DiscardCardFactory;
import pojo.BenchPokemon;
import pojo.Card;
import pojo.Deck;
import pojo.DiscardCard;
import pojo.Game;
import pojo.Hand;

public class EndTurnCommand implements ICommand {

	@Override
	public void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, Exception {
		long myId = (Long)request.getSession().getAttribute("userid"); 
		long gameId = (Long)(request.getAttribute("game"));
		int version = Integer.parseInt(request.getParameter("version"));
		Hand opponentHand = null;
		Game game = GameInputMapper.findById(gameId);
		if(game==null) {
			throw new Exception("Game was not found"); 
		}
		request.getServletContext().log(version + " is my version");
		game.setVersion(version);
		if(myId != game.getPlayer1() && myId!= game.getPlayer2()) {
			throw new Exception("This is not your game");
		}
		if(myId != game.getCurrentPlayer()) {
			throw new Exception("It is not your turn");
		}
		if(myId == game.getPlayer1()) {
			opponentHand = HandInputMapper.find(game.getId(), game.getPlayer2());
			game.setCurrentPlayer(game.getPlayer2());
		}
		else if(myId == game.getPlayer2()) {
			opponentHand = HandInputMapper.find(game.getId(), game.getPlayer1());
			game.setCurrentPlayer(game.getPlayer1());
		}
		Hand myHand = HandInputMapper.find(game.getId(), myId);
		if(myHand.getHandSize() >7) {
			List<BenchPokemon> myBench = BenchPokemonInputMapper.findAllByHandId(myHand.getId());
			long myDeckId = myId == game.getPlayer1() ? game.getDeck1() : game.getDeck2();
			Deck deck = DeckInputMapper.findById(myDeckId);
			List<DiscardCard> discardPile = DiscardCardInputMapper.find(game.getId(), myId);
			List<Card> myCurrentHand = myHand.getCurrentHand(deck, myBench,discardPile);
			int indexMin = 0;
			for(int i = 1; i< myCurrentHand.size(); i++) {
				if(myCurrentHand.get(i).getId() < myCurrentHand.get(indexMin).getId()) {
					indexMin = i;
				}
			}
			DiscardCardFactory.createNew(game.getId(), myId, myCurrentHand.get(indexMin).getId());
			myHand.setDiscardSize(myHand.getDiscardSize()+1);
			myHand.setHandSize(myHand.getHandSize() -1);
		}
		opponentHand.setHandSize(opponentHand.getHandSize() + 1);
		opponentHand.setDeckSize(opponentHand.getDeckSize()-1);
		
		request.setAttribute("message", "Player #" + myId + " ended his turn.");
	}
}
