package PageControllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

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
 * Servlet implementation class RetireFromGame
 */
@WebServlet("/Retire")
public class RetirePC extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RetirePC() {
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
			long myId = (Long)request.getSession().getAttribute("userid"); 
			long gameId = Long.parseLong(request.getParameter("game"));
			Game game = GameInputMapper.findById(gameId);
			if(game==null) {
				throw new Exception("Game was not found");
			}
			
			if(myId == game.getPlayer1()) {
				game.setP1Status("retired");
			}
			else if(myId == game.getPlayer2()) {
				game.setP2Status("retired");
			}
			else {
				throw new Exception("This is not your game.");
			}
			UoW.getCurrent().commit();
			request.setAttribute("message", "User '" + myId + "' retired successfully from game " + game.getId());
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/success.jsp").forward(request, response);
			DbRegistry.closeConnection();
		}
		catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("message", "SQLException");
			DbRegistry.closeConnection();
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		}
		catch(NullPointerException e) {
			e.printStackTrace();
			DbRegistry.closeConnection();
			request.setAttribute("message", "Need to log in.");
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		}
		catch(NumberFormatException e) {
			e.printStackTrace();
			DbRegistry.closeConnection();
			request.setAttribute("message", "Please provide a game");
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		}
		catch(Exception e) {
			e.printStackTrace();
			DbRegistry.closeConnection();
			request.setAttribute("message", e.getMessage());
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
