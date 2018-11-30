package PageControllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import InputMapper.UserInputMapper;
import core.UoW;
import database.DbRegistry;
import pojo.User;

/**
 * Servlet implementation class ListPlayers
 */
@WebServlet("/ListPlayers")
public class ListPlayersPC extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListPlayersPC() {
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
			
			long userId = (Long)request.getSession(true).getAttribute("userid");
			List<User> players = new ArrayList<User>();
			players = UserInputMapper.findAll();
			
			request.setAttribute("players", players);
			if(!response.isCommitted())
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/players.jsp").forward(request, response);
			DbRegistry.closeConnection();
			
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("message", "SQL error");
			DbRegistry.closeConnection();
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		}
		catch(NullPointerException e){
			throw new Exception("Need to log in.");
		}
		catch(Exception e) {
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
