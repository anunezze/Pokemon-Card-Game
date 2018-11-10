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
import rdg.GameRDG;
import rdg.HandRDG;

/**
 * Servlet implementation class DrawCardPC
 */
@WebServlet("/DrawCard")
public class DrawCardPC extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DrawCardPC() {
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
			
			try{
				gameId = Long.parseLong(request.getParameter("game"));
			}
			catch(NumberFormatException e) {
				request.setAttribute("message", "Please provide a game");
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
				return;
			}
			
			GameRDG game = GameRDG.find(gameId);
			if(myId != game.getPlayer1() && myId != game.getPlayer2()) {
				request.setAttribute("message", "This is not your game");
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
				return;
			}
			HandRDG hand = HandRDG.find(game.getId(), myId);
			hand.setDeckSize(hand.getDeckSize()-1);
			hand.setHandSize(hand.getHandSize() + 1);
			hand.update();
			
			request.setAttribute("message", "User '" + myId + "' draw a card.");
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/success.jsp").forward(request, response);
		}
		catch(SQLException e) {
			e.printStackTrace();
			request.setAttribute("message", "SQL error");
			Connection connection = new DbRegistry().getConnection();
			try {
				connection.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
			return;
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
