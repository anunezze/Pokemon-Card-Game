package dispatcher;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import command.ICommand;
import command.UploadDeckCommand;
import core.UoW;

public class UploadDeckDispatcher extends Dispatcher {
	
	
	public UploadDeckDispatcher(HttpServletRequest request, HttpServletResponse response) {
		super(request, response);
	}
	
	@Override
	public void execute() throws ServletException, IOException {
		ICommand command = new UploadDeckCommand();
		try {
			command.processRequest(request, response);
			UoW.getCurrent().commit();
			request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/success.jsp").forward(request, response);
		}
		catch(SQLException e) {
			e.printStackTrace();
			request.setAttribute("message", "SQL error");
			request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		}
		catch(NullPointerException e){
			request.setAttribute("message", "Can't upload a deck if not logged in.");
			request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		}
		catch(Exception e) {
			request.setAttribute("message", e.getMessage());
			request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/failure.jsp").forward(request, response);
		}

	}

}
