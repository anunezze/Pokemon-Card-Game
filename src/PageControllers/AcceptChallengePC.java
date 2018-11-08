package PageControllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rdg.ChallengeRDG;
import rdg.DeckRDG;
import rdg.GameRDG;
import util.ChallengeStatus;

/**
 * Servlet implementation class AcceptChallenge
 */
@WebServlet("/AcceptChallenge")
public class AcceptChallengePC extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AcceptChallengePC() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			long userid = (Long)request.getSession(true).getAttribute("userid");
		}
		catch(NullPointerException e){
			request.setAttribute("message", "Need to log in.");
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		}
		long challengeId = (Long.parseLong(request.getParameter("challenge")));
		ChallengeRDG challenge = null;
		try {
			challenge = ChallengeRDG.find(challengeId);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}		
		if(challenge == null){
			request.setAttribute("message", "Challenge was not found");
			if(!response.isCommitted())
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		}
		challenge.setStatus(ChallengeStatus.ACCEPTED);
		try {
			challenge.update();
			DeckRDG deck1 = DeckRDG.findByPlayer(challenge.getChallenger());
			DeckRDG deck2 = DeckRDG.findByPlayer(challenge.getChallengee());
			if(deck1==null){
				request.setAttribute("message", "Challenger doesn't have a deck.");
				if(!response.isCommitted())
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
			}
			else if(deck2 == null){
				request.setAttribute("message", "Challengee doesn't have a deck.");
				if(!response.isCommitted())
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
			}
			GameRDG game = new GameRDG(0, challenge.getChallenger(), challenge.getChallengee(), deck1.getId(), deck2.getId());
			game.insert();
			request.setAttribute("message", "Challenge was accepted");
			if(!response.isCommitted())
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/success.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("message", "SQL error");
			if(!response.isCommitted())
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", e.getMessage());
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
