package PageControllers;

import java.io.IOException;
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
import factory.BenchPokemonFactory;
import pojo.BenchPokemon;
import pojo.Card;
import pojo.Deck;
import pojo.Game;
import pojo.Hand;

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
			DbRegistry.newConnection();
			UoW.newUoW();
			long myId =(Long)request.getSession().getAttribute("userid"); 
			int version = Integer.parseInt(request.getParameter("version"));
			long gameId = Long.parseLong(request.getParameter("game"));
			long cardId = Long.parseLong(request.getParameter("cardId"));
			Game game = GameInputMapper.findById(gameId);
			if(myId != game.getPlayer1() && myId != game.getPlayer2() || game == null) {
				throw new Exception("Not your game or game not found");
			}
			Hand hand = HandInputMapper.find(game.getId(), myId);
			if(hand.getHandSize() == 0) {
				throw new Exception("You don't have a card in hand.");
			}
			Deck deck = null;
			if(myId == game.getPlayer1()) {
				deck = DeckInputMapper.findById(game.getDeck1());
			} else {
				deck = DeckInputMapper.findById(game.getDeck2());
			}			
			List<BenchPokemon> bench = BenchPokemonInputMapper.findAllByHandId(hand.getId());
			List<Card> currentHand = hand.getCurrentHand(deck,bench);
			boolean cardFound = false;
			for(int i = 0; i< currentHand.size(); i++) {
				if(currentHand.get(i).getId() == cardId) {
					cardFound = true;
				}
			}
			if(!cardFound) {
				throw new Exception("You don't have card #" + cardId + " in your hand.");
			}
			BenchPokemon pokemonBench = BenchPokemonFactory.createNew(hand.getId(), cardId, new ArrayList<Long>());
			hand.setBenchSize(hand.getBenchSize() + 1);
			hand.setHandSize(hand.getHandSize() - 1);
			request.setAttribute("message", "User '" + myId + "' put a pokemon in his bench" + hand.getBenchId());
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/success.jsp").forward(request, response);
			game.markDirty();
			UoW.getCurrent().commit();
			DbRegistry.closeConnection();
		}
		catch(SQLException e) {
			e.printStackTrace();
			request.setAttribute("message", "SQLException");
			DbRegistry.closeConnection();
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		}
		catch(NullPointerException e) {
			DbRegistry.closeConnection();
			request.setAttribute("message", "Need to log in.");
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
