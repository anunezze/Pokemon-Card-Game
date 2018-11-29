package dispatcher;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import command.ICommand;
import command.ViewSpecificDeckCommand;
import core.UoW;

public class ViewSpecificDeckDispatcher extends Dispatcher {
	
	public ViewSpecificDeckDispatcher(HttpServletRequest request, HttpServletResponse response) {
		super(request,response);
	}
	@Override
	public void execute() throws ServletException, IOException {
		ICommand command = new ViewSpecificDeckCommand();
		try {
			command.processRequest(request, response);
			UoW.getCurrent().commit();
			request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/specificDeck.jsp").forward(request, response);
		}
		catch(Exception e) {
			request.setAttribute("message", e.getMessage());
			request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		}
	}
}
