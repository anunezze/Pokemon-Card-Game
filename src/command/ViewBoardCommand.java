package command;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import InputMapper.BenchPokemonInputMapper;
import InputMapper.GameInputMapper;
import InputMapper.HandInputMapper;
import pojo.BenchPokemon;
import pojo.Game;
import pojo.Hand;

public class ViewBoardCommand implements ICommand {

	@Override
	public void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, Exception {
		long userId = (Long)request.getSession(true).getAttribute("userid");
		long gameId = (Long)(request.getAttribute("game"));
		Game game = GameInputMapper.findById(gameId);
		if(game ==null || userId != game.getPlayer1() && userId != game.getPlayer2()) {
			throw new Exception("This is not your game user " + userId);
		}
		Hand hand1 = HandInputMapper.find(game.getId(), game.getPlayer1());
		Hand hand2 = HandInputMapper.find(game.getId(), game.getPlayer2());
		List<BenchPokemon> bench1 = BenchPokemonInputMapper.findAllByHandId(hand1.getId()); 
		List<BenchPokemon> bench2 = BenchPokemonInputMapper.findAllByHandId(hand2.getId());
		
		request.setAttribute("game", game);
		request.setAttribute("hand1", hand1);
		request.setAttribute("hand2", hand2);
		request.setAttribute("bench1", bench1);
		request.setAttribute("bench2", bench2);
	}
}
