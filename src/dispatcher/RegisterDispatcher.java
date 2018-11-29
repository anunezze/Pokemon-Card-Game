package dispatcher;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import command.RegisterCommand;
import core.UoW;

public class RegisterDispatcher extends Dispatcher{	
	
	public RegisterDispatcher(HttpServletRequest request, HttpServletResponse response) {
		super(request, response);
	}
	
	@Override
	public void execute() throws ServletException, IOException {
		RegisterCommand c = new RegisterCommand();
		try {
			c.processRequest(request,response);
			UoW.getCurrent().commit();
			request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/success.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("message", "SQLException");
			request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		}
		catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("message", e.getMessage());
			request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		}
	}
}
