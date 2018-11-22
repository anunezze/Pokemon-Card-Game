package PageControllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import InputMapper.UserInputMapper;
import core.UoW;
import database.DbRegistry;
import factory.UserFactory;
import pojo.User;
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
			DbRegistry.newConnection();
			if(user==null || user.isEmpty() || password==null || password.isEmpty()) {
				request.setAttribute("message", "Please enter both a username and a password.");
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
			} 
			else {
				UoW.newUoW();
				User u = UserInputMapper.find(user);
				if(u != null) {
					request.setAttribute("message", "That user has already registered.");
					getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
					return;
				} 
				else {
					u = UserFactory.createNew(IdGenerator.getInstance().createID(), 1, user, HashUtil.hash(password));
					UoW.getCurrent().commit();
					long id = u.getId();
					request.setAttribute("id", id);
					request.getSession(true).setAttribute("userid", id);
					request.setAttribute("message", "User '" + user + "' has been successfully registered.");
					getServletContext().getRequestDispatcher("/WEB-INF/jsp/success.jsp").forward(request, response);
					return;
				}
			}
			DbRegistry.getConnection().close();
		}
		catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("message", "SQLException");
			try {
				DbRegistry.getConnection().close();
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
