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
 * Servlet implementation class ViewBoardPC
 */
@WebServlet("/ViewBoard")
public class ViewBoardPC extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewBoardPC() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long userId = -1;
		try {
			try{
				userId = (Long)request.getSession(true).getAttribute("userid");
			}
			catch(NullPointerException e){
				request.setAttribute("message", "Not logged in.");
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
			}
			long gameId = -1;
			gameId = Long.parseLong(request.getParameter("game"));
			GameRDG game = GameRDG.find(gameId);
			HandRDG hand1 = HandRDG.find(game.getId(), game.getPlayer1());
			HandRDG hand2 = HandRDG.find(game.getId(), game.getPlayer2());
			
			if(userId != game.getPlayer1() && userId != game.getPlayer2()) {
				request.setAttribute("message", "This is not your game.");
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
				return;
			}
			if(game == null || hand1 == null || hand2 == null) {
				request.setAttribute("message", "Was not found in db.");
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
				return;
			}
			request.setAttribute("game", game);
			request.setAttribute("hand1", hand1);
			request.setAttribute("hand2", hand2);
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/board.jsp").forward(request, response);
		}catch (SQLException e) {
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
