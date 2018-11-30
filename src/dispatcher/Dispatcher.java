package dispatcher;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Dispatcher {
	protected HttpServletRequest request;
	protected HttpServletResponse response;	
	
	public Dispatcher(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}
	
	public abstract void execute() throws ServletException, IOException;
	public void setAttribute(String name, Object o) {
		request.setAttribute(name, o);
	}
}
