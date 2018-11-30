package command;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import InputMapper.ChallengeInputMapper;
import InputMapper.DeckInputMapper;
import factory.GameFactory;
import factory.HandFactory;
import pojo.Challenge;
import pojo.Deck;
import pojo.Game;
import pojo.Hand;
import util.ChallengeStatus;

public class AcceptChallengeCommand implements ICommand {

	@Override
	public void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, Exception {
		long userid = (Long)request.getSession(true).getAttribute("userid");
		long challengeId = (Long)(request.getAttribute("challenge"));
		long challengeeDeckId = (Long.parseLong(request.getParameter("deck")));
		int challengeVersion = (Integer.parseInt(request.getParameter("version")));
		Challenge challenge = ChallengeInputMapper.findById(challengeId);
		
		if(challenge == null){
			throw new Exception("Challenge doens't exist");
		}
		if(challenge.getChallengee() != userid) {
			throw new Exception("This is not your challenge.");
		}
		if(challenge.getChallenger() == userid) {
			throw new Exception("Can't accept your own challenge.");
		}
		challenge.setStatus(ChallengeStatus.ACCEPTED); 
		challenge.setVersion(challengeVersion);
		Deck challengeeDeck = DeckInputMapper.findById(challengeeDeckId);
		if(challengeeDeck == null){
			throw new Exception ("Deck " + challengeeDeckId + " was not found.");
		} else if(challengeeDeck.getOwnerId() != challenge.getChallengee()) {
			throw new Exception ("Deck " + challengeeDeckId + " is not yours.");
		}
		Game newGame = GameFactory.createNew(
				challenge.getChallenger(), 
				challenge.getChallengee(), 
				challenge.getChallenger(), 
				challenge.getChallengerDeck(), 
				challengeeDeck.getId(), 
				"playing", 
				"playing");
		
		Hand hand1 = HandFactory.createNew(newGame.getId(), newGame.getPlayer1(), 40);
		Hand hand2 = HandFactory.createNew(newGame.getId(), newGame.getPlayer2(), 40);
		hand1.setDeckSize(hand1.getDeckSize()-1);
		hand1.setHandSize(hand1.getHandSize() + 1);
		request.setAttribute("message", "Challenge was accepted");
		request.setAttribute("userid", newGame.getPlayer1());
		request.setAttribute("game", newGame.getId());
	}
}
