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
import pojo.BenchPokemon;
import pojo.Card;
import pojo.Deck;
import pojo.DiscardCard;
import pojo.Game;
import pojo.Hand;

public class ViewHandCommand implements ICommand {

	@Override
	public void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, Exception {
		long myId = (Long)request.getSession().getAttribute("userid"); 
		long gameId = (Long)(request.getAttribute("game"));
		Game game = GameInputMapper.findById(gameId);
		
		Hand hand = HandInputMapper.find(game.getId(), myId);
		Deck deck = null;
		if(myId == game.getPlayer1()) {
			deck = DeckInputMapper.findById(game.getDeck1());
		} else if(myId == game.getPlayer2()) {
			deck = DeckInputMapper.findById(game.getDeck2());
		} else {
			throw new Exception("Not your game");
		}
		List<BenchPokemon> bench = BenchPokemonInputMapper.findAllByHandId(hand.getId());
		List<DiscardCard> discardPile = DiscardCardInputMapper.find(game.getId(), myId);
		List<Card> cardsInHand = hand.getCurrentHand(deck,bench,discardPile);
		request.setAttribute("cards", cardsInHand);
	}
}
