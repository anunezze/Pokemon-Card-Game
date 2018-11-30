package core;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DbRegistry;
import dispatcher.AcceptChallengeDispatcher;
import dispatcher.ChallengePlayerDispatcher;
import dispatcher.Dispatcher;
import dispatcher.EndTurnDispatcher;
import dispatcher.ListChallengeDispatcher;
import dispatcher.ListGameDispatcher;
import dispatcher.ListPlayerDispatcher;
import dispatcher.LoginDispatcher;
import dispatcher.LogoutDispatcher;
import dispatcher.PlayPokemonToBenchDispatcher;
import dispatcher.RefuseChallengeDispatcher;
import dispatcher.RegisterDispatcher;
import dispatcher.RetireDispatcher;
import dispatcher.UploadDeckDispatcher;
import dispatcher.ViewBoardDispatcher;
import dispatcher.ViewDeckDispatcher;
import dispatcher.ViewDiscardDispatcher;
import dispatcher.ViewHandDispatcher;
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
		else if(urlArray.length == 3 && urlArray[1].equals("Deck") && urlArray[2].matches("\\d+")){
			dispatcher=	new ViewSpecificDeckDispatcher(request,response);
			dispatcher.setAttribute("deckId", Long.parseLong(urlArray[2]));
		}
		else if(url.equals("/Player")) {
			dispatcher = new ListPlayerDispatcher(request, response);
		}
		else if(urlArray.length == 4 && urlArray[1].equals("Player") && urlArray[2].matches("\\d+") && urlArray[3].equals("Challenge")) {
			dispatcher = new ChallengePlayerDispatcher(request,response);
			dispatcher.setAttribute("player", Long.parseLong(urlArray[2]));
		}
		else if(url.equals("/Challenge")) {
			dispatcher = new ListChallengeDispatcher(request,response);
		}
		else if(urlArray.length == 4 &&
				urlArray[1].equals("Challenge") && 
				urlArray[2].matches("\\d+") && 
				(urlArray[3].equals("Withdraw")||urlArray[3].equals("Refuse"))) {
			dispatcher = new RefuseChallengeDispatcher(request,response);
			dispatcher.setAttribute("challenge", Long.parseLong(urlArray[2]));
		}
		else if(urlArray.length == 4 && 
				urlArray[1].equals("Challenge") 
				&& urlArray[2].matches("\\d+") 
				&& urlArray[3].equals("Accept")) {
			dispatcher = new AcceptChallengeDispatcher(request,response);
			dispatcher.setAttribute("challenge", Long.parseLong(urlArray[2]));
		}
		else if(url.equals("/Game")) {
			dispatcher = new ListGameDispatcher(request,response);
		}
		else if(urlArray.length == 6 &&
				urlArray[1].equals("Game") && 
				urlArray[2].matches("\\d+") && 
				urlArray[3].equals("Hand") && 
				urlArray[4].matches("\\d+") && 
				urlArray[5].equals("Play")) {
			dispatcher = new PlayPokemonToBenchDispatcher(request,response);
			dispatcher.setAttribute("game", Long.parseLong(urlArray[2]));
			dispatcher.setAttribute("cardId", Long.parseLong(urlArray[4]));
		}
		else if(urlArray.length == 4 && 
				urlArray[1].equals("Game") && 
				urlArray[2].matches("\\d+") && 
				urlArray[3].equals("Hand")) {
			dispatcher = new ViewHandDispatcher(request,response);
			dispatcher.setAttribute("game", Long.parseLong(urlArray[2]));
		}
		else if(urlArray.length == 3 && urlArray[1].equals("Game") && urlArray[2].matches("\\d+")) {
			dispatcher = new ViewBoardDispatcher(request,response);
			dispatcher.setAttribute("game", Long.parseLong(urlArray[2]));
		}
		else if(urlArray.length == 4 && urlArray[1].equals("Game") && urlArray[2].matches("\\d+") & urlArray[3].equals("Retire")) {
			dispatcher = new RetireDispatcher(request,response);
			dispatcher.setAttribute("game", Long.parseLong(urlArray[2]));
		}
		else if(urlArray.length == 4 && urlArray[1].equals("Game") && urlArray[2].matches("\\d+") & urlArray[3].equals("EndTurn")) {
			dispatcher = new EndTurnDispatcher(request,response);
			dispatcher.setAttribute("game", Long.parseLong(urlArray[2]));
		}
		else if(urlArray.length == 6 &&
				urlArray[1].equals("Game") && 
				urlArray[2].matches("\\d+") && 
				urlArray[3].equals("Player") &&
				urlArray[4].matches("\\d+") &&
				urlArray[5].equals("Discard")) {
			dispatcher = new ViewDiscardDispatcher(request,response);
			dispatcher.setAttribute("game", Long.parseLong(urlArray[2]));
			dispatcher.setAttribute("player", Long.parseLong(urlArray[4]));
		}
	}
}