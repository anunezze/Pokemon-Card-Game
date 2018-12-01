package tdg;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DbRegistry;

public class PokemonEnergyTDG {
	public static void insert(long pokemonId, long energyId) throws SQLException {
		String query = "INSERT INTO pokemon_energy (pokemon_id,energy_id) VALUES (?,?)";
		PreparedStatement ps = DbRegistry.getConnection().prepareStatement(query);
		ps.setLong(1, pokemonId);
		ps.setLong(2, energyId);
		ps.executeUpdate();
	}
	public static ResultSet findAll(long pokemonId) throws SQLException {
		String query = "SELECT * FROM pokemon_energy WHERE pokemon_id =?";
		PreparedStatement ps = DbRegistry.getConnection().prepareStatement(query);
		ps.setString(1, Long.toString(pokemonId));
		return ps.executeQuery();
	}
	public static void deleteAll(long pokemonId) throws SQLException {
		String query = "DELETE FROM pokemon_energy WHERE pokemon_id=?";
		PreparedStatement ps = DbRegistry.getConnection().prepareStatement(query);
		ps.setLong(1, pokemonId);
		ps.executeUpdate();
	}
}
