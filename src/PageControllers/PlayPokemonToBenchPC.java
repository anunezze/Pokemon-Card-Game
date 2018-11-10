package PageControllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DbRegistry;
import rdg.DeckRDG;
import rdg.GameRDG;
import rdg.HandRDG;

/**
 * Servlet implementation class PlayPokemonToBench
 */
@WebServlet("/PlayPokemonToBench")
public class PlayPokemonToBenchPC extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PlayPokemonToBenchPC() {
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
			if(hand.getHandSize() == 0) {
				request.setAttribute("message", "You don't have a card in hand.");
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
				return;
			}
//			DeckRDG deck = DeckRDG.findByPlayer(myId);
			
			request.setAttribute("message", "User '" + myId + "' put a pokemon in his bench" + game.getId());
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/success.jsp").forward(request, response);
		}
		catch(SQLException e) {
			request.setAttribute("message", "SQLException");
			Connection connection = new DbRegistry().getConnection();
			try {
				connection.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
