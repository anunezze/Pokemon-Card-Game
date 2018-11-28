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
import InputMapper.HandInputMapper;
import core.UoW;
import database.DbRegistry;
import pojo.Game;
import pojo.Hand;

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
			DbRegistry.newConnection();
			UoW.newUoW();
			long myId = (Long)request.getSession().getAttribute("userid"); 
			
			long gameId = Long.parseLong(request.getParameter("game"));
						
			Game game = GameInputMapper.findById(gameId);
			if(myId != game.getPlayer1() && myId != game.getPlayer2()) {
				throw new Exception("This is not your game");
			}
			Hand hand = HandInputMapper.find(game.getId(), myId);
			hand.setDeckSize(hand.getDeckSize()-1);
			hand.setHandSize(hand.getHandSize() + 1);
			
			request.setAttribute("message", "User '" + myId + "' draw a card.");
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/success.jsp").forward(request, response);
			game.markDirty();
			UoW.getCurrent().commit();
			DbRegistry.closeConnection();
		}
		catch(SQLException e) {
			e.printStackTrace();
			request.setAttribute("message", "SQL error");
			DbRegistry.closeConnection();
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		}
		catch(NullPointerException e) {
			e.printStackTrace();
			request.setAttribute("message", "Need to log in.");
			DbRegistry.closeConnection();
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
			return;
		}
		catch(NumberFormatException e) {
			e.printStackTrace();
			request.setAttribute("message", "Please provide a game");
			DbRegistry.closeConnection();
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
			return;
		}
		catch(Exception e) {
			e.printStackTrace();;
			request.setAttribute("message", e.getMessage() );
			DbRegistry.closeConnection();
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
