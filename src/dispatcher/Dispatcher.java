package dispatcher;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Dispatcher {
	protected HttpServletRequest request;
	protected HttpServletResponse response;	
	
	public Dispatcher(HttpServletRequest myRequest, HttpServletResponse myResponse) {
		this.request = myRequest;
		this.response = myResponse;
	}
	
	public abstract void execute() throws ServletException, IOException;
	public void setAttribute(String name, Object o) {
		request.setAttribute(name, o);
	}
}
