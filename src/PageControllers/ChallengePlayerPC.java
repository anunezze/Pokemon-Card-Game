package PageControllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import InputMapper.DeckInputMapper;
import InputMapper.UserInputMapper;
import core.UoW;
import database.DbRegistry;
import factory.ChallengeFactory;
import pojo.Challenge;
import pojo.IDeck;
import pojo.User;

/**
 * Servlet implementation class ChallengePlayer
 */
@WebServlet("/ChallengePlayer")
public class ChallengePlayerPC extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChallengePlayerPC() {
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
			long challengeeID = Long.parseLong(request.getParameter("player"));
			long myId = (Long)request.getSession().getAttribute("userid");
			long challengerDeckID = Long.parseLong(request.getParameter("deck"));
			
			if(myId == challengeeID){
				throw new Exception("Cannot challenge yourself");
			}
			User challengee = UserInputMapper.find(challengeeID);
			if(challengee == null){
				throw new Exception("This challengee doesn't exist.");
			}
			IDeck challengerDeck = DeckInputMapper.findById(challengerDeckID);
			if(challengerDeck == null || challengerDeck.getOwnerId() != myId) {
				if(challengerDeck == null) {
					getServletContext().log("null");
				}else {
					getServletContext().log(challengerDeck.getOwnerId() + "");
				}
				throw new Exception("This deck doesn't exist or is not yours");
			}
			Challenge newChallenge = ChallengeFactory.createNew(myId, challengeeID, challengerDeckID);
			UoW.getCurrent().commit();
			request.setAttribute("message", "Challenge was sent to " + challengee.getUsername());
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/success.jsp").forward(request, response);
			
			DbRegistry.closeConnection();
		}
		catch(SQLException s) {
			s.printStackTrace();
			request.setAttribute("message", "SQL error");
			DbRegistry.closeConnection();
			if(!response.isCommitted())
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		}
		catch(NumberFormatException e) {
			e.printStackTrace();
			request.setAttribute("message", "No challenge entered.");
			DbRegistry.closeConnection();
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		}
		catch(NullPointerException e){
			e.printStackTrace();
			request.setAttribute("message", "Cannot challenge a player if you are not logged in.");
			DbRegistry.closeConnection();
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		}
		catch (Exception e) {
			e.printStackTrace();
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
