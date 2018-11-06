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
		
		if(user==null || user.isEmpty() || password==null || password.isEmpty()) {
			request.setAttribute("message", "Please enter both a username and a password.");
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		} 
		else {
			UserRDG u = null;
			try {
				u = UserRDG.find(user);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(u != null) {
				request.setAttribute("message", "That user has already registered.");
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
			} 
		else {
			
			u = new UserRDG(0, user, HashUtil.hash(password));
			try {
				u.insert();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			long id = u.getId();
			request.setAttribute("id", id);
			request.getSession(true).setAttribute("userid", id);
			
			request.setAttribute("message", "User '" + user + "' has been successfully registered.");
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/success.jsp").forward(request, response);
			}
		}
	}

	public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
		
	}

}
