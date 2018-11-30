package dispatcher;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import command.ICommand;
import command.ListPlayerCommand;
import core.UoW;

public class ListPlayerDispatcher extends Dispatcher {

	public ListPlayerDispatcher(HttpServletRequest request, HttpServletResponse response) {
		super(request, response);
	}

	@Override
	public void execute() throws ServletException, IOException {
		ICommand command = new ListPlayerCommand();
		try {
			command.processRequest(request, response);
			UoW.getCurrent().commit();
			request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/players.jsp").forward(request, response);
		}
		 catch (SQLException e) {
				e.printStackTrace();
				request.setAttribute("message", "SQL error");
				request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
			}
			catch(NullPointerException e){
				request.setAttribute("message", "Need to log in.");
				request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
			}
			catch(Exception e) {
				
				request.setAttribute("message", e.getMessage());
				request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
			}
	}

}
