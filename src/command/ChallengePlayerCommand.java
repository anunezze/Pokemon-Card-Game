package command;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import InputMapper.ChallengeInputMapper;
import InputMapper.DeckInputMapper;
import InputMapper.UserInputMapper;
import factory.ChallengeFactory;
import pojo.Challenge;
import pojo.IDeck;
import pojo.User;
import util.ChallengeStatus;

public class ChallengePlayerCommand implements ICommand {

	@Override
	public void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, Exception {
		long challengeeID = (Long)(request.getAttribute("player"));
		long myId = (Long)request.getSession().getAttribute("userid");
		long challengerDeckID = Long.parseLong(request.getParameter("deck"));
		request.getServletContext().log(challengeeID + " challengee id");
		List<Challenge> allChallenges = ChallengeInputMapper.findAll();
		for(int i =0; i< allChallenges.size(); i++) {
			if(allChallenges.get(i).getChallenger() == myId && allChallenges.get(i).getChallengee() == challengeeID && allChallenges.get(i).getStatus() == ChallengeStatus.OPEN) {
				throw new Exception("You already have an open challenge with user #" + challengeeID);
			}

		}
		if(myId == challengeeID){
			throw new Exception("Cannot challenge yourself");
		}
		User challengee = UserInputMapper.find(challengeeID);
		if(challengee == null){
			throw new Exception("This challengee doesn't exist.");
		}
		IDeck challengerDeck = DeckInputMapper.findById(challengerDeckID);
		if(challengerDeck == null || challengerDeck.getOwnerId() != myId) {
			throw new Exception("This deck doesn't exist or is not yours");
		}
		ChallengeFactory.createNew(myId, challengeeID, challengerDeckID);
		request.setAttribute("message", "Challenge was sent to " + challengee.getUsername());
	}

}
