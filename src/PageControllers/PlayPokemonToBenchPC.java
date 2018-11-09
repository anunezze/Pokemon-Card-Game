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
		}
		catch(SQLException e) {
			
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
