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
		Long challengeId = (Long.parseLong(request.getParameter("challenge")));
		ChallengeRDG challenge = null;
		UserRDG challenger = null;
		try {
			UserRDG challengee = UserRDG.find((Long)request.getSession().getAttribute("userid"));
			challenge = ChallengeRDG.find(challengeId);
			challenger = UserRDG.find(challenge.getChallengee());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}		
		challenge.setStatus(ChallengeStatus.ACCEPTED);
		try {
			challenge.update();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
