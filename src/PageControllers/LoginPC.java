package PageControllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rdg.UserRDG;
import util.HashUtil;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class LoginPC extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginPC() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("user");
		String password = request.getParameter("pass");
		UserRDG user = null;
		try {
			user = UserRDG.find(username);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(user == null || !user.getPassword().equals(HashUtil.hash(password))) {
			request.setAttribute("message", "I do not recognize that username and password combination.");
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		} 
		else {
			long id = user.getId();
			request.getSession(true).setAttribute("userid", id);
			request.setAttribute("message", "User '" + user.getUsername() + "' has been successfully logged in.");
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
