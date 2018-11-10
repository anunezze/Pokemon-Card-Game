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
import util.ChallengeStatus;

/**
 * Servlet implementation class RefuseChallenge
 */
@WebServlet("/RefuseChallenge")
public class RefuseChallengePC extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RefuseChallengePC() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long userid =-1;
		try {
			try{
				userid = (Long)request.getSession(true).getAttribute("userid");
			}
			catch(NullPointerException e){
				request.setAttribute("message", "Need to log in.");
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
				return;
			}
			long challengeId = (Long.parseLong(request.getParameter("challenge")));
			ChallengeRDG challenge = ChallengeRDG.find(challengeId);
			
			if(challenge == null){
				request.setAttribute("message", "Challenge was not found");
				if(!response.isCommitted())
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
				return;
			}
			
			if(userid == challenge.getChallenger()) {
				challenge.setStatus(ChallengeStatus.WITHDRAW);
			}
			else if(userid == challenge.getChallengee()) {
				challenge.setStatus(ChallengeStatus.REFUSED);
			}
			else {
				request.setAttribute("message", "This is not your challenge.");
				if(!response.isCommitted())
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
				return;
			}
			
			challenge.update();
			request.setAttribute("message", "Challenge was refused");
			if(!response.isCommitted())
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/success.jsp").forward(request, response);
		}
		catch (SQLException e) {
			request.setAttribute("message", "SQLException");
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
