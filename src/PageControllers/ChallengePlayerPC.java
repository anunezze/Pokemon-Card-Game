package PageControllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rdg.ChallengeRDG;
import rdg.UserRDG;
import util.ChallengeStatus;

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
		long challengeeID = Long.parseLong(request.getParameter("challengee"));
		long myId = -1;
		try{
			myId = (Long)request.getSession().getAttribute("userid");
		} catch(NullPointerException e){
			request.setAttribute("message", "Cannot challenge a player if you are not logged in.");
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
			return;
		}
		
		UserRDG challengee = null;
		UserRDG challenger = null;
		try {
			challenger = UserRDG.find(myId);
			challengee = UserRDG.find(challengeeID);
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("message", "SQL PRBLEM");
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
			return;
		}
		if(challengee == null){
			request.setAttribute("message", "This player doesn't exist.");
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
			return;
		}
		else {
			ChallengeRDG challenge = new ChallengeRDG(0, challenger.getId(), challengee.getId(), ChallengeStatus.OPEN);
			try {
				challenge.insert();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			request.setAttribute("message", "Challenge was sent to " + challengee.getUsername());
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/success.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
