package PageControllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rdg.ChallengeRDG;

/**
 * Servlet implementation class ListChallenges
 */
@WebServlet("/ListChallenges")
public class ListChallengesPC extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListChallengesPC() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<ChallengeRDG> challenges = null;
		try {
			challenges = ChallengeRDG.findAllOpen();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(challenges == null){
			request.setAttribute("message", "SQL problem");
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		}
		else{
			request.setAttribute("challenges", challenges);
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/challenges.jsp").forward(request, response);
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
