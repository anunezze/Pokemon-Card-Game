package command;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import InputMapper.ChallengeInputMapper;
import pojo.Challenge;

public class ListChallengeCommand implements ICommand {

	@Override
	public void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, Exception {
		long myId = (Long)request.getSession().getAttribute("userid");
		
		List<Challenge> challenges = ChallengeInputMapper.findAll();
		request.setAttribute("challenges", challenges);
	}

}
