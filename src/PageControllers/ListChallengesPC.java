package PageControllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DbRegistry;
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
		try {
			try{
				long myId = (Long)request.getSession().getAttribute("userid");
			} catch(NullPointerException e){
				request.setAttribute("message", "Need to log in.");
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
				return;
			}
			List<ChallengeRDG> challenges = new ArrayList<ChallengeRDG>();
			challenges = ChallengeRDG.findAllOpen();
			if(challenges == null){
				request.setAttribute("message", "Challenge was not found");
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
				return;
			}
			else{
				request.setAttribute("challenges", challenges);
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/challenges.jsp").forward(request, response);
				return;
			}
		}
		catch(SQLException e) {
			request.setAttribute("message", "SQL error");
			Connection connection = new DbRegistry().getConnection();
			try {
				connection.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
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
