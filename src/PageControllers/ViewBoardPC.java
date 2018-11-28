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

import InputMapper.BenchPokemonInputMapper;
import InputMapper.GameInputMapper;
import InputMapper.HandInputMapper;
import core.UoW;
import database.DbRegistry;
import pojo.BenchPokemon;
import pojo.Game;
import pojo.Hand;

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
		try {
			DbRegistry.newConnection();
			UoW.newUoW();
			long userId = (Long)request.getSession(true).getAttribute("userid");
			
			long gameId = Long.parseLong(request.getParameter("game"));
			Game game = GameInputMapper.findById(gameId);
			if(game ==null || userId != game.getPlayer1() && userId != game.getPlayer2()) {
				throw new Exception("This is not your game user " + userId);
			}
			Hand hand1 = HandInputMapper.find(game.getId(), game.getPlayer1());
			Hand hand2 = HandInputMapper.find(game.getId(), game.getPlayer2());
			List<BenchPokemon> bench1 = BenchPokemonInputMapper.findAllByHandId(hand1.getId()); 
			List<BenchPokemon> bench2 = BenchPokemonInputMapper.findAllByHandId(hand2.getId());
			
			request.setAttribute("game", game);
			request.setAttribute("hand1", hand1);
			request.setAttribute("hand2", hand2);
			request.setAttribute("bench1", bench1);
			request.setAttribute("bench2", bench2);
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/board.jsp").forward(request, response);
			DbRegistry.closeConnection();
		}
		catch (SQLException e) {
			request.setAttribute("message", "SQLException");
			DbRegistry.closeConnection();
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		}
		catch(NullPointerException e){
			DbRegistry.closeConnection();
			request.setAttribute("message", "Not logged in.");
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		}
		catch(Exception e) {
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
