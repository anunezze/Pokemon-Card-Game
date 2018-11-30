package dispatcher;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import command.ChallengePlayerCommand;
import command.ICommand;
import core.UoW;

public class ChallengePlayerDispatcher extends Dispatcher {

	public ChallengePlayerDispatcher(HttpServletRequest request, HttpServletResponse response) {
		super(request, response);
	}

	@Override
	public void execute() throws ServletException, IOException {
		ICommand c = new ChallengePlayerCommand();
		try {
			c.processRequest(request, response);
			UoW.getCurrent().commit();
			request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/success.jsp").forward(request, response);
		}
		catch(NumberFormatException e) {
			e.printStackTrace();
			request.setAttribute("message", "No challenge entered.");
			request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		}
		catch(NullPointerException e){
			e.printStackTrace();
			request.setAttribute("message", "Cannot challenge a player if you are not logged in.");
			request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		}
		catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", e.getMessage());
			request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		}
	}

}
