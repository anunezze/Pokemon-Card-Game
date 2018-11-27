package PageControllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import InputMapper.GameInputMapper;
import core.UoW;
import database.DbRegistry;
import pojo.Game;

/**
 * Servlet implementation class ListGame
 */
@WebServlet("/ListGames")
public class ListGamesPC extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListGamesPC() {
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
			List<Game> games = GameInputMapper.findAll();
			request.setAttribute("games", games);
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/games.jsp").forward(request, response);
			DbRegistry.closeConnection();
		}
		catch(NullPointerException e){
			DbRegistry.closeConnection();
			request.setAttribute("message", "Need to log in.");
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		}
		catch (SQLException e) {
			request.setAttribute("message", e.getMessage());
			DbRegistry.closeConnection();
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
