package command;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import InputMapper.ChallengeInputMapper;
import pojo.Challenge;
import util.ChallengeStatus;

public class RefuseChallengeCommand implements ICommand {

	@Override
	public void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, Exception {
		long userid = (Long)request.getSession(true).getAttribute("userid");
		
		long challengeId = (Long)(request.getAttribute("challenge"));
		int version = (Integer.parseInt(request.getParameter("version")));
		Challenge challenge = ChallengeInputMapper.findById(challengeId);
		
		if(challenge == null){
			throw new Exception("Challenge was not found");
		}
		
		if(userid == challenge.getChallenger()) {
			challenge.setStatus(ChallengeStatus.WITHDRAW);
			request.setAttribute("message", "Challenge was withdraw.");
		}
		else if(userid == challenge.getChallengee()) {
			challenge.setStatus(ChallengeStatus.REFUSED);
			request.setAttribute("message", "Challenge was refused.");
		}
		else {
			throw new Exception("This is not your challenge.");
		}
		challenge.setVersion(version);

	}
}
