package PageControllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import InputMapper.DeckInputMapper;
import core.UoW;
import database.DbRegistry;
import pojo.IDeck;


/**
 * Servlet implementation class ViewDeck
 */
@WebServlet("/ViewDeck")
public class ViewDeckPC extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewDeckPC() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		DbRegistry.newConnection();
		UoW.newUoW();
		try {
			long myId = -1;
			myId = (Long)request.getSession().getAttribute("userid");
			List<IDeck> decks = DeckInputMapper.findAllByOwner(myId);
			if(decks.isEmpty()) {
				getServletContext().log("deck is empty");
				throw new Exception("You have no deck");
			}
			request.setAttribute("decks", decks);
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/deck.jsp").forward(request, response);
			DbRegistry.closeConnection();
		}
		catch(NullPointerException e) {
			e.getStackTrace();
			DbRegistry.closeConnection();
			request.setAttribute("message", "Need to log in.");
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		}
		catch(SQLException e) {
			e.getStackTrace();
			DbRegistry.closeConnection();
			request.setAttribute("message", "SQL Exception");
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		}
		catch(Exception e) {
			e.getStackTrace();
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
