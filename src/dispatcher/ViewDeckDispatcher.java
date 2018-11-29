package dispatcher;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import command.ICommand;
import command.ViewDeckCommand;
import core.UoW;

public class ViewDeckDispatcher extends Dispatcher {
	
	public ViewDeckDispatcher(HttpServletRequest request, HttpServletResponse response) {
		super(request,response);
	}
	@Override
	public void execute() throws ServletException, IOException {
		ICommand command = new ViewDeckCommand();
		try {
			command.processRequest(request, response);
			UoW.getCurrent().commit();
			request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/deck.jsp").forward(request, response);
		} 
		catch(NullPointerException e) {
			e.getStackTrace();
			request.setAttribute("message", "Need to log in.");
			request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		}
		catch(SQLException e) {
			e.getStackTrace();
			request.setAttribute("message", "SQL Exception");
			request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		}
		catch(Exception e) {
			e.getStackTrace();
			request.setAttribute("message", e.getMessage());
			request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		}
	}

}
