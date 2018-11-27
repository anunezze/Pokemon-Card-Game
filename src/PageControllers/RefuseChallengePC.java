package PageControllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import InputMapper.ChallengeInputMapper;
import core.UoW;
import database.DbRegistry;
import pojo.Challenge;
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
			DbRegistry.newConnection();
			UoW.newUoW();
			userid = (Long)request.getSession(true).getAttribute("userid");
			
			long challengeId = (Long.parseLong(request.getParameter("challenge")));
			int version = (Integer.parseInt(request.getParameter("version")));
			Challenge challenge = ChallengeInputMapper.findById(challengeId);
			
			if(challenge == null){
				throw new Exception("Challenge was not found");
			}
			
			if(userid == challenge.getChallenger()) {
				challenge.setStatus(ChallengeStatus.WITHDRAW);
				request.setAttribute("message", "Challenge was withdraw.");
			}
			else if(userid == challenge.getChallengee()) {
				challenge.setStatus(ChallengeStatus.REFUSED);
				request.setAttribute("message", "Challenge was refused.");
			}
			else {
				throw new Exception("This is not your challenge.");
			}
			challenge.setVersion(version);
			UoW.getCurrent().commit();
			DbRegistry.closeConnection();
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/success.jsp").forward(request, response);			
		}
		catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("message", "SQLException");
			DbRegistry.closeConnection();
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		}
		catch(NullPointerException e){
			e.printStackTrace();
			DbRegistry.closeConnection();
			request.setAttribute("message", "Need to log in.");
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		}
		catch(Exception e) {
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
		doGet(request, response);
	}

}
