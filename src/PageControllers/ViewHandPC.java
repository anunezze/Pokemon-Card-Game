package PageControllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DbRegistry;
import pojo.Card;
import rdg.DeckRDG;
import rdg.GameRDG;
import rdg.HandRDG;

/**
 * Servlet implementation class ViewHandPC
 */
@WebServlet("/ViewHand")
public class ViewHandPC extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewHandPC() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			long myId = -1;
			try{
				myId = (Long)request.getSession().getAttribute("userid"); 
			}
			catch(NullPointerException e) {
				request.setAttribute("message", "Need to log in.");
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
				return;
			}
			long gameId = -1;
			gameId = Long.parseLong(request.getParameter("game"));
			GameRDG game = GameRDG.find(gameId);
			if(myId != game.getPlayer1() && myId != game.getPlayer2()) {
				request.setAttribute("message", "Not your game.");
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
				return;
			}
			HandRDG hand = HandRDG.find(game.getId(), myId);
			DeckRDG deck;
			try {
				deck = DeckRDG.findByPlayer(myId);
			} catch (Exception e) {
				request.setAttribute("message", e.getMessage());
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
				return;
			}
			List<Card> cardsInHand = this.getCurrentHand(deck, hand);
			request.setAttribute("cards", cardsInHand);
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/hand.jsp").forward(request, response);
			
		}
		catch(SQLException e) {
			Connection connection = new DbRegistry().getConnection();
			try {
				connection.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private List<Card> getCurrentHand(DeckRDG deck, HandRDG hand){
		List<Card> currentHand = new ArrayList<Card>();
		int skip = hand.getDiscardSize();
		for(int i = skip; i < skip + hand.getHandSize(); i++) {
			currentHand.add(deck.getCards().get(i));
		}
		
		return currentHand;
	}
}
