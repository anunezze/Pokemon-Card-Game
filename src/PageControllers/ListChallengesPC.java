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

import InputMapper.ChallengeInputMapper;
import core.UoW;
import database.DbRegistry;
import pojo.Challenge;

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
			DbRegistry.newConnection();
			UoW.newUoW();
			long myId = (Long)request.getSession().getAttribute("userid");
			
			List<Challenge> challenges = ChallengeInputMapper.findAll();
			request.setAttribute("challenges", challenges);
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/challenges.jsp").forward(request, response);
			DbRegistry.closeConnection();
		}
		catch(SQLException e) {
			e.printStackTrace();
			request.setAttribute("message", "SQL error");
			DbRegistry.closeConnection();
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		}
		catch(NullPointerException e){
			e.printStackTrace();
			request.setAttribute("message", "Need to log in.");
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
