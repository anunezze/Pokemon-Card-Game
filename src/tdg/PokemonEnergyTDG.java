package tdg;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DbRegistry;

public class PokemonEnergyTDG {
	public static void insert(long pokemonId, long energyId) {
		
	}
	public static ResultSet findAll(long pokemonId) throws SQLException {
		String query = "SELECT * FROM pokemon_enery WHERE pokemon_id =?";
		PreparedStatement ps = DbRegistry.getConnection().prepareStatement(query);
		ps.setString(1, Long.toString(pokemonId));
		return ps.executeQuery();
	}
}
