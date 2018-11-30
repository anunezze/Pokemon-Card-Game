package PageControllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import InputMapper.BenchPokemonInputMapper;
import InputMapper.DeckInputMapper;
import InputMapper.GameInputMapper;
import InputMapper.HandInputMapper;
import core.UoW;
import database.DbRegistry;
import pojo.BenchPokemon;
import pojo.Card;
import pojo.Deck;
import pojo.Game;
import pojo.Hand;

/**
 * Servlet implementation class ViewHandPC
 */
@WebServlet("/ViewHand")
public class ViewHandPC extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewHandPC() {
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
			
			Hand hand = HandInputMapper.find(game.getId(), myId);
			Deck deck = null;
			if(myId == game.getPlayer1()) {
				deck = DeckInputMapper.findById(game.getDeck1());
			} else if(myId == game.getPlayer2()) {
				deck = DeckInputMapper.findById(game.getDeck2());
			} else {
				throw new Exception("Not your game");
			}
			List<BenchPokemon> bench = BenchPokemonInputMapper.findAllByHandId(hand.getId());
			List<Card> cardsInHand = hand.getCurrentHand(deck,bench);
			request.setAttribute("cards", cardsInHand);
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/hand.jsp").forward(request, response);
			DbRegistry.closeConnection();
		}
		catch(SQLException e) {
			e.printStackTrace();
			DbRegistry.closeConnection();
			request.setAttribute("message", "SQL error");
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
			
		}
		catch(NumberFormatException e) {
			e.printStackTrace();
			DbRegistry.closeConnection();
			request.setAttribute("message", "No version specified");
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		}
		catch(NullPointerException e) {
			e.printStackTrace();
			DbRegistry.closeConnection();
			request.setAttribute("message", "Need to log in.");
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		}
		catch (Exception e) {
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
