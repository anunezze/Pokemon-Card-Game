package PageControllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		Object userId = request.getSession(true).getAttribute("userid");
		if(userId==null){
			request.setAttribute("message", "There is no one logged in.");
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
			return;
		}
		long gameId = -1;
		gameId = Long.parseLong(request.getParameter("game"));
		GameRDG game = null;
		HandRDG hand1 = null;
		HandRDG hand2 = null;
		
		try {
			game = GameRDG.find(gameId);
			hand1 = HandRDG.find(game.getId(), game.getPlayer1());
			hand2 = HandRDG.find(game.getId(), game.getPlayer2());
		} catch (SQLException e) {
			request.setAttribute("message", "SQL error");
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
			return;
		}
		if(game == null || hand1 == null || hand2 == null) {
			request.setAttribute("message", "Was not found in db.");
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
