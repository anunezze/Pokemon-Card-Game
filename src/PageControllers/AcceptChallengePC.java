package PageControllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import InputMapper.ChallengeInputMapper;
import InputMapper.DeckInputMapper;
import core.UoW;
import database.DbRegistry;
import factory.GameFactory;
import factory.HandFactory;
import pojo.Challenge;
import pojo.Deck;
import pojo.Game;
import pojo.Hand;
import rdg.ChallengeRDG;
import rdg.DeckRDG;
import rdg.GameRDG;
import rdg.HandRDG;
import util.ChallengeStatus;
import util.IdGenerator;

/**
 * Servlet implementation class AcceptChallenge
 */
@WebServlet("/AcceptChallenge")
public class AcceptChallengePC extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AcceptChallengePC() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			DbRegistry.newConnection();
			UoW.newUoW();
			long userid = (Long)request.getSession(true).getAttribute("userid");
			boolean process = true;
			long challengeId = (Long.parseLong(request.getParameter("challenge")));
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
			UoW.getCurrent().commit();
			DbRegistry.closeConnection();
			request.setAttribute("message", "Challenge was accepted");
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/success.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("message", "SQL error");
			DbRegistry.closeConnection();
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		} catch(NullPointerException e){
			DbRegistry.closeConnection();
			request.setAttribute("message", "Need to log in.");
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		} catch (Exception e) {
			DbRegistry.closeConnection();
			e.printStackTrace();
			request.setAttribute("message", e.getMessage());
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
