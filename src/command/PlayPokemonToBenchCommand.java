package command;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import InputMapper.BenchPokemonInputMapper;
import InputMapper.DeckInputMapper;
import InputMapper.DiscardCardInputMapper;
import InputMapper.GameInputMapper;
import InputMapper.HandInputMapper;
import factory.BenchPokemonFactory;
import pojo.BenchPokemon;
import pojo.Card;
import pojo.Deck;
import pojo.DiscardCard;
import pojo.Game;
import pojo.Hand;

public class PlayPokemonToBenchCommand implements ICommand {

	@Override
	public void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, Exception {
		long myId =(Long)request.getSession().getAttribute("userid"); 
		int version = Integer.parseInt(request.getParameter("version"));
		long gameId = (Long)(request.getAttribute("game"));
		long cardId = (Long)(request.getAttribute("cardId"));
		Game game = GameInputMapper.findById(gameId);
		if(myId != game.getPlayer1() && myId != game.getPlayer2() || game == null) {
			throw new Exception("Not your game or game not found");
		}
		if(game.getCurrentPlayer() != myId) {
			throw new Exception("It is not your turn");
		}
		Hand hand = HandInputMapper.find(game.getId(), myId);
		if(hand.getHandSize() == 0) {
			throw new Exception("You don't have a card in hand.");
		}
		Deck deck = null;
		if(myId == game.getPlayer1()) {
			deck = DeckInputMapper.findById(game.getDeck1());
		} else {
			deck = DeckInputMapper.findById(game.getDeck2());
		}			
		List<BenchPokemon> bench = BenchPokemonInputMapper.findAllByHandId(hand.getId());
		List<DiscardCard> discardPile = DiscardCardInputMapper.find(game.getId(), myId);
		List<Card> currentHand = hand.getCurrentHand(deck,bench,discardPile);
		boolean cardFound = false;
		for(int i = 0; i< currentHand.size(); i++) {
			if(currentHand.get(i).getId() == cardId) {
				cardFound = true;
			}
		}
		if(!cardFound) {
			throw new Exception("You don't have card #" + cardId + " in your hand.");
		}
		BenchPokemon pokemonBench = BenchPokemonFactory.createNew(hand.getId(), cardId, new ArrayList<Long>());
		hand.setBenchSize(hand.getBenchSize() + 1);
		hand.setHandSize(hand.getHandSize() - 1);
		request.setAttribute("message", "User '" + myId + "' put a pokemon in his bench" + hand.getBenchId());
		game.setVersion(version);
		game.markDirty();
	}
}
