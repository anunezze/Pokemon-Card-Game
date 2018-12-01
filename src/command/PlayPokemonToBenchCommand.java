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
import factory.DiscardCardFactory;
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
		Card card = null;
		for(int i = 0; i< currentHand.size() && card == null; i++) {
			if(currentHand.get(i).getId() == cardId) {
				card = currentHand.get(i);
			}
		}
		if(card == null) {
			throw new Exception("You don't have card #" + cardId + " in your hand.");
		}
		if(card.getType() == 'p' && card.getBase() == null) {
			this.playPokemonToBench(hand, cardId, myId);
		}
		else if(card.getType() == 'p' && card.getBase() != null) {
			this.playEvolvePokemon();
		}
		else if(card.getType() == 'e') {
			long pokemonId = Long.parseLong(request.getParameter("pokemon"));
			this.playEnergy(pokemonId, bench, cardId, hand,request);
		}
		else if(card.getType() == 't') {
			this.playTrainer(game.getId(), myId, card.getId(), hand);
		}
		request.setAttribute("message", "User '" + myId + "' played card #" + card.getId() +" of type '" + card.getType() + "'");
		game.setVersion(version);
		game.markDirty();
	}
	private void playPokemonToBench(Hand hand, long cardId, long myId) {
		BenchPokemon pokemonBench = BenchPokemonFactory.createNew(hand.getId(), cardId, new ArrayList<Long>());
		hand.setBenchSize(hand.getBenchSize() + 1);
		hand.setHandSize(hand.getHandSize() - 1);
		
	}
	private void playTrainer(long gameId, long playerId, long cardId,Hand hand) {
		DiscardCardFactory.createNew(gameId, playerId, cardId);
		hand.setDiscardSize(hand.getDiscardSize()+1);
		hand.setHandSize(hand.getHandSize() -1);
	}
	private void playEvolvePokemon() {
		
	}
	private void playEnergy(long pokemonId, List<BenchPokemon> bench, long cardId, Hand hand,HttpServletRequest request) throws Exception {
		BenchPokemon bp = null;
		for(int i = 0; i<bench.size() && bp == null; i++) {
			if(bench.get(i).getPokemonId() == pokemonId) {
				bp = bench.get(i);
			}
		}
		if(bp == null) {
			throw new Exception("Pokemon in bench not found");
		}
		bp.addEnergy(cardId);
		request.getServletContext().log(bp.getEnergies().size() + "is my energy size");
		hand.setBenchSize(hand.getBenchSize() + 1);
		hand.setHandSize(hand.getHandSize() - 1);
	}
}
