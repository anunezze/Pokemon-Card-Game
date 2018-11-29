package core;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DbRegistry;
import dispatcher.Dispatcher;
import dispatcher.LoginDispatcher;
import dispatcher.LogoutDispatcher;
import dispatcher.RegisterDispatcher;
import dispatcher.UploadDeckDispatcher;
import dispatcher.ViewDeckDispatcher;
import dispatcher.ViewSpecificDeckDispatcher;

/**
 * Servlet implementation class AppFC
 */
@WebServlet("/AppFC")
public class AppFC extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Dispatcher dispatcher;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AppFC() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DbRegistry.newConnection();
		UoW.newUoW();
		request.getServletContext().log("front runned at " + request.getPathInfo() + " with method " + request.getMethod());
		setDispatcher(request,response);
		dispatcher.execute();
		DbRegistry.closeConnection();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private void setDispatcher(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getPathInfo();
		String[] urlArray = url.split("/");
		for(int i = 0; i < urlArray.length; i++) {
			request.getServletContext().log("index " + i + " text: " + urlArray[i]);
		}
		if(url.equals("/Player/Register")) {
			dispatcher = new RegisterDispatcher(request,response);
		}
		else if(url.equals("/Player/Login")) {
			dispatcher = new LoginDispatcher(request,response);
		}
		else if(url.equals("/Deck")){
			if(request.getMethod().equals("POST")) {
				dispatcher = new UploadDeckDispatcher(request,response);
			}
			else if(request.getMethod().equals("GET")) {
				dispatcher = new ViewDeckDispatcher(request,response);
			}
			
		}
		else if(url.equals("/Player/Logout")) {
			dispatcher = new LogoutDispatcher(request, response);
		}
		else if(urlArray[1].equals("Deck") && urlArray[2].matches("\\d+")){
			dispatcher=	new ViewSpecificDeckDispatcher(request,response);
			dispatcher.setAttribute("deckId", Long.parseLong(urlArray[2]));
		}
	}
}