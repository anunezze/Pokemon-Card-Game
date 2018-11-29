package command;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import InputMapper.UserInputMapper;
import core.UoW;
import database.DbRegistry;
import pojo.User;
import util.HashUtil;

public class LoginCommand implements ICommand{

	
	@Override
	public void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, Exception {
		String username = request.getParameter("user");
		String password = request.getParameter("pass");
		User user = UserInputMapper.find(username);
		
		if(user == null || !user.getPassword().equals(HashUtil.hash(password))) {
			throw new Exception("I do not recognize that username and password combination.");			
		} 
		long id = user.getId();
		request.getSession(true).setAttribute("userid", id);
		request.setAttribute("message", "User '" + user.getUsername() + "' has been successfully logged in.");
		
		DbRegistry.closeConnection();
	}


}
