package PageControllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rdg.GameRDG;

/**
 * Servlet implementation class RetireFromGame
 */
@WebServlet("/RetireFromGame")
public class RetireFromGamePC extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RetireFromGamePC() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			if(!response.isCommitted())
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
			return;
		}
		GameRDG game = null;
		try {
			game = GameRDG.find(gameId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(game==null) {
			request.setAttribute("message", "Game was not found");
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
			return;
		}
		
		if(game.getPlayer1() != myId && game.getPlayer2() != myId) {
			request.setAttribute("message", "This is not your game. Cannot quit.");
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
