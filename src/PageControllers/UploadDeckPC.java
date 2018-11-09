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

import pojo.Card;
import rdg.DeckRDG;
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
		try{
			userId = (Long)request.getSession(true).getAttribute("userid");
		}
		catch(NullPointerException e){
			request.setAttribute("message", "Can't upload a deck if not logged in.");
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		}
		String deckParameter = request.getParameter("deck");
		DeckRDG deck = null;
		try {
			deck = new DeckRDG(IdGenerator.getInstance().createID(), userId, deckParameter);
			deck.insert();
		} catch(SQLException e){
			e.getStackTrace();
			request.setAttribute("message", "SQL error");
			if(!response.isCommitted())
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		}
		catch (Exception e) {
			request.setAttribute("message", e.getMessage());
			if(!response.isCommitted())
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		}		
		request.setAttribute("message", "Upload was done successfully");
		if(!response.isCommitted())
		getServletContext().getRequestDispatcher("/WEB-INF/jsp/success.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	private String deckString(){
		  return  "e \"Lightning\"\n" +
					"e \"Lightning\"\n" +
					"p \"Pikachu\"\n" +
					"e \"Lightning\"\n" +
					"e \"Lightning\"\n" +
					"e \"Lightning\"\n" +
					"p \"Pikachu\"\n" +
					"p \"Meowth\"\n" +
					"e \"Lightning\"\n" +
					"t \"Tierno\"\n" +
					"t \"Tierno\"\n" +
					"e \"Lightning\"\n" +
					"e \"Lightning\"\n" +
					"e \"Lightning\"\n" +
					"p \"Pikachu\"\n" +
					"e \"Lightning\"\n" +
					"e \"Lightning\"\n" +
					"e \"Lightning\"\n" +
					"p \"Pikachu\"\n" +
					"p \"Meowth\"\n" +
					"e \"Lightning\"\n" +
					"t \"Tierno\"\n" +
					"t \"Tierno\"\n" +
					"e \"Lightning\"\n" +
					"e \"Lightning\"\n" +
					"e \"Lightning\"\n" +
					"p \"Pikachu\"\n" +
					"e \"Lightning\"\n" +
					"e \"Lightning\"\n" +
					"e \"Lightning\"\n" +
					"p \"Pikachu\"\n" +
					"p \"Meowth\"\n" +
					"e \"Lightning\"\n" +
					"t \"Tierno\"\n" +
					"t \"Tierno\"\n" +
					"e \"Lightning\"\n" +
					"e \"Lightning\"\n" +
					"e \"Lightning\"\n" +
					"e \"Lightning\"\n" +
					"e \"Lightning\"\n" +
					"e \"Lightning\"\n";
	}
}
