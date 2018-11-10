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
import rdg.UserRDG;
import util.HashUtil;
import util.IdGenerator;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class RegisterPC extends HttpServlet{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterPC() {
        super();
    }

	/**
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws SQLException 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user = request.getParameter("user");
		String password = request.getParameter("pass");
		try {
			if(user==null || user.isEmpty() || password==null || password.isEmpty()) {
				request.setAttribute("message", "Please enter both a username and a password.");
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
			} 
			else {
				UserRDG u = UserRDG.find(user);
				if(u != null) {
					request.setAttribute("message", "That user has already registered.");
					getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
					return;
				} 
				else {
					
					u = new UserRDG(IdGenerator.getInstance().createID(), user, HashUtil.hash(password));
					u.insert();
					long id = u.getId();
					request.setAttribute("id", id);
					request.getSession(true).setAttribute("userid", id);
					
					request.setAttribute("message", "User '" + user + "' has been successfully registered.");
					getServletContext().getRequestDispatcher("/WEB-INF/jsp/success.jsp").forward(request, response);
					return;
				}
			}
		}
		catch (SQLException e) {
			request.setAttribute("message", "SQLException");
			Connection connection = new DbRegistry().getConnection();
			try {
				connection.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
		
	}

}
