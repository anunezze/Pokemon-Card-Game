package command;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import InputMapper.UserInputMapper;
import core.UoW;
import database.DbRegistry;
import factory.UserFactory;
import pojo.User;
import util.HashUtil;
import util.IdGenerator;


public class RegisterCommand {

	public void processRequest(HttpServletRequest request, HttpServletResponse response) throws SQLException, Exception{
		String user = request.getParameter("user");
		String password = request.getParameter("pass");
		if(user==null || user.isEmpty() || password==null || password.isEmpty()) {
			throw new Exception("Please enter both a username and a password.");
		} 
		else {
			User u = UserInputMapper.find(user);
			if(u != null) {
				throw new Exception("That user has already registered."); 
			} 
			u = UserFactory.createNew(IdGenerator.getInstance().createID(), 1, user, HashUtil.hash(password));
			long id = u.getId();
			request.setAttribute("id", id);
			request.getSession(true).setAttribute("userid", id);
			request.setAttribute("message", "User '" + user + "' has been successfully registered.");
		}
	}

}
