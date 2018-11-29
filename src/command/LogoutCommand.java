package command;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import InputMapper.UserInputMapper;
import database.DbRegistry;
import pojo.User;

public class LogoutCommand implements ICommand {

	@Override
	public void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, Exception {
		Object userId = request.getSession(true).getAttribute("userid");
		if(userId==null){
			throw new Exception("There is no one logged in.");
		}
		
		long id = (Long)userId;
		User u = UserInputMapper.find(id);
		
		if(u == null){
			throw new Exception("User was not found with id:" + id);
		}
		request.getSession(true).invalidate();
		request.setAttribute("message", "User '" + u.getUsername() + "' has been successfully logged out.");		
	}

}
