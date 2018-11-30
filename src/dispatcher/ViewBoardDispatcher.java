package dispatcher;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import command.ICommand;
import command.ViewBoardCommand;
import core.UoW;
import database.DbRegistry;

public class ViewBoardDispatcher extends Dispatcher {

	public ViewBoardDispatcher(HttpServletRequest request, HttpServletResponse response) {
		super(request, response);
	}

	@Override
	public void execute() throws ServletException, IOException {
		ICommand c = new ViewBoardCommand();
		try {
			c.processRequest(request, response);
			UoW.getCurrent().commit();
			request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/board.jsp").forward(request, response);
		}
		catch(NullPointerException e){
			DbRegistry.closeConnection();
			request.setAttribute("message", "Not logged in.");
			request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		}
		catch(Exception e) {
			DbRegistry.closeConnection();
			request.setAttribute("message", e.getMessage());
			request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		}
	}
}
