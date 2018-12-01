package InputMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.IdentityMap;
import factory.GameFactory;
import finder.GameFinder;
import pojo.Game;
import tdg.GameTDG;

public abstract class GameInputMapper {
	public static List<Game> findAll() throws SQLException{
		ResultSet rs = GameFinder.findAll();
		List<Game> result = new ArrayList<Game>();
		while(rs.next()) {
			result.add(GameFactory.createClean(
					rs.getInt("id"), 
					rs.getInt("version"), 
					rs.getInt("player1"), 
					rs.getInt("player2"), 
					rs.getInt("current_player"), 
					rs.getInt("deck1"), 
					rs.getInt("deck2"), 
					rs.getString("p1_status"),
					rs.getString("p2_status")));
		}
		
		return result;
	}
	
	public static Game findById(long id) throws SQLException {
		Game g = null;
		if(IdentityMap.contains(id)) {
			return (Game)IdentityMap.find(id);
		}
		ResultSet rs = GameFinder.find(id);
		if(rs.next()) {
			g = GameFactory.createClean(
					rs.getInt("id"), 
					rs.getInt("version"), 
					rs.getInt("player1"), 
					rs.getInt("player2"), 
					rs.getInt("current_player"), 
					rs.getInt("deck1"), 
					rs.getInt("deck2"), 
					rs.getString("p1_status"),
					rs.getString("p2_status"));
		}
		
		return g;
		
	}
}
