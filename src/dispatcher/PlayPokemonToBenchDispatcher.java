package dispatcher;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import command.ICommand;
import command.PlayPokemonToBenchCommand;
import core.UoW;

public class PlayPokemonToBenchDispatcher extends Dispatcher {

	public PlayPokemonToBenchDispatcher(HttpServletRequest request, HttpServletResponse response) {
		super(request, response);
	}

	@Override
	public void execute() throws ServletException, IOException {
		ICommand c = new PlayPokemonToBenchCommand();
		try {
			c.processRequest(request, response);
			UoW.getCurrent().commit();
			request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/success.jsp").forward(request, response);
		}
		catch(NullPointerException e) {
			e.printStackTrace();
			request.setAttribute("message", "Need to log in.");
			request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		}
		catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("message", e.getMessage());
			request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		}
	}
}
