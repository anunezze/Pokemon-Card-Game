package PageControllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import OutputMapper.DeckOutputMapper;
import core.UoW;
import database.DbRegistry;
import factory.DeckFactory;
import pojo.Deck;
import util.IdGenerator;

/**
 * Servlet implementation class UploadDeck
 */
@WebServlet("/UploadDeck")
public class UploadDeckPC extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadDeckPC() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long userId = -1;
		
		try {
			DbRegistry.newConnection();
			UoW.newUoW();
			try{
				userId = (Long)request.getSession(true).getAttribute("userid");
			}
			catch(NullPointerException e){
				throw new Exception("Can't upload a deck if not logged in.");
			}
			String deckParameter = this.uploadDeckForTest();//request.getParameter("deck");
			Deck deck = DeckFactory.createNew(IdGenerator.getInstance().createID(),1, userId, deckParameter);
			DeckOutputMapper.insert(deck);
			request.setAttribute("message", "Upload was done successfully");
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/success.jsp").forward(request, response);			
			DbRegistry.closeConnection();
		}
		catch(SQLException e) {
			e.printStackTrace();
			request.setAttribute("message", "SQL error");
			DbRegistry.closeConnection();
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		}
		catch(Exception e) {
			request.setAttribute("message", e.getMessage());
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
			DbRegistry.closeConnection();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private String uploadDeckForTest() {
		return "e \"Fire\"\n" +
				"e \"Fire\"\n" +
				"p \"Charizard\"\n" +
				"e \"Fire\"\n" +
				"e \"Fire\"\n" +
				"e \"Fire\"\n" +
				"p \"Charizard\"\n" +
				"p \"Meowth\"\n" +
				"e \"Fire\"\n" +
				"t \"Misty\"\n" +
				"t \"Misty\"\n" +
				"e \"Fire\"\n" +
				"e \"Fire\"\n" +
				"e \"Fire\"\n" +
				"p \"Charizard\"\n" +
				"e \"Fire\"\n" +
				"e \"Fire\"\n" +
				"e \"Fire\"\n" +
				"p \"Charizard\"\n" +
				"p \"Meowth\"\n" +
				"e \"Fire\"\n" +
				"t \"Misty\"\n" +
				"t \"Misty\"\n" +
				"e \"Fire\"\n" +
				"e \"Fire\"\n" +
				"e \"Fire\"\n" +
				"p \"Charizard\"\n" +
				"e \"Fire\"\n" +
				"e \"Fire\"\n" +
				"e \"Fire\"\n" +
				"p \"Charizard\"\n" +
				"p \"Meowth\"\n" +
				"e \"Fire\"\n" +
				"t \"Misty\"\n" +
				"t \"Misty\"\n" +
				"e \"Fire\"\n" +
				"e \"Fire\"\n" +
				"e \"Fire\"\n" +
				"p \"Charizard\"\n" +
				"e \"Fire\"\n";
	}
}
