package PageControllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DbRegistry;
import rdg.ChallengeRDG;
import rdg.DeckRDG;
import rdg.UserRDG;
import util.ChallengeStatus;
import util.IdGenerator;

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
			long challengeeID = -1;
			try {
				challengeeID = Long.parseLong(request.getParameter("player"));
			}
			catch(NumberFormatException e) {
				request.setAttribute("message", "No challenge entered.");
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
			}
			long myId = -1;
			DeckRDG deck1 = null;
			try{
				myId = (Long)request.getSession().getAttribute("userid");
			} catch(NullPointerException e){
				request.setAttribute("message", "Cannot challenge a player if you are not logged in.");
				if(!response.isCommitted())
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
			}
			if(myId == challengeeID){
				request.setAttribute("message", "Cannot challenge yourself");
				if(!response.isCommitted())
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
			}
			
			UserRDG challengee = UserRDG.find(challengeeID);
			UserRDG challenger = UserRDG.find(myId);
			
			if(challengee == null){
				request.setAttribute("message", "This player doesn't exist.");
				if(!response.isCommitted())
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
			}
			
			try {
				deck1 = DeckRDG.findByPlayer(challenger.getId());
			}  catch (Exception e) {
				request.setAttribute("message", e.getMessage());
				if(!response.isCommitted())
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
			}
			if(challenger == null) {
				request.setAttribute("message", "Challenger was not found in DB");
				if(!response.isCommitted())
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
			}
			if(challengee == null) {
				request.setAttribute("message", "Challengee was not found in DB");
				if(!response.isCommitted())
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
			}
			if(deck1 == null){
				request.setAttribute("message", "You don't have a deck");
				if(!response.isCommitted())
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
			}
			if(challenger != null && challengee != null && deck1 != null) {
				ChallengeRDG challenge = new ChallengeRDG(IdGenerator.getInstance().createID(), challenger.getId(), challengee.getId(), ChallengeStatus.OPEN);
				
				challenge.insert();
				request.setAttribute("message", "Challenge was sent to " + challengee.getUsername());
				if(!response.isCommitted())
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/success.jsp").forward(request, response);
			}
		}
		catch(SQLException s) {
			s.printStackTrace();
			request.setAttribute("message", "SQL error");
			Connection connection = new DbRegistry().getConnection();
			try {
				connection.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			if(!response.isCommitted())
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
