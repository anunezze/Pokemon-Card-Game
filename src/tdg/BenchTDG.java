package tdg;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DbRegistry;

public class BenchTDG {
	public static void insert(long id, int version, long handId, long pokemonId, boolean energy, long base) throws SQLException {
		String query = "INSERT INTO bench (id,version,hand_id,pokemon_id,energy,base) VALUES (?,?,?,?,?,?);";
		PreparedStatement ps = DbRegistry.getConnection().prepareStatement(query);
		ps.setLong(1, id);
		ps.setInt(2, version);
		ps.setLong(3, handId);
		ps.setLong(4, pokemonId);
		ps.setInt(5, energy ? 1 : 0);
		ps.setLong(6, base);
		ps.executeUpdate();
	}
	
	public static ResultSet findById(long id) throws SQLException {
		String query = "SELECT * FROM bench WHERE id =?;";
		PreparedStatement ps = DbRegistry.getConnection().prepareStatement(query);
		ps.setString(1, Long.toString(id));
		return ps.executeQuery();
	}
	
	public static ResultSet findAllByHandId(long handId) throws SQLException {
		String query = "SELECT * FROM bench WHERE hand_id =?;";
		PreparedStatement ps = DbRegistry.getConnection().prepareStatement(query);
		ps.setString(1, Long.toString(handId));
		return ps.executeQuery();
	}
	
	public static int update(int version,long handId, boolean energy, long pokemonId, long base) throws SQLException {
		String query = "UPDATE bench SET version=?, energy =?, pokemon_id=?, base=? WHERE hand_id=? AND version=?";
		PreparedStatement ps = DbRegistry.getConnection().prepareStatement(query);
		ps.setInt(1, version + 1);
		ps.setInt(2, energy ? 1 : 0);
		ps.setLong(3, pokemonId);
		ps.setLong(4, base);
		ps.setLong(5, handId);
		ps.setInt(6, version);
		return ps.executeUpdate();
	}
}
