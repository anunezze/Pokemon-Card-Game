package InputMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import factory.BenchPokemonFactory;
import pojo.BenchPokemon;
import tdg.BenchTDG;
import tdg.PokemonEnergyTDG;

public abstract class BenchPokemonInputMapper {
	public static BenchPokemon find(long id) throws SQLException {
		BenchPokemon result = null;
		List<Long> energies = new ArrayList<Long>();
		ResultSet rs = BenchTDG.findById(id);
		if(rs.next()) {
			if(rs.getInt("energy") == 1) {
				energies = findAllEnergies(rs.getLong("pokemon_id"));
			}
			result = BenchPokemonFactory.createClean(
					rs.getLong("id"),
					rs.getInt("version"), 
					rs.getLong("hand_id"), 
					rs.getLong("pokemon_id"), 
					energies,
					rs.getLong("base"));
		}
		
		return result;
	}
	
	public static List<BenchPokemon> findAllByHandId(long handId) throws SQLException {
		List<BenchPokemon> result = new ArrayList<BenchPokemon>();
		ResultSet rs = BenchTDG.findAllByHandId(handId);
		
		while(rs.next()) {
			List<Long> energies = new ArrayList<Long>();
			if(rs.getInt("energy") == 1) {
				energies = findAllEnergies(rs.getLong("pokemon_id"));
			}
			result.add(BenchPokemonFactory.createClean(
					rs.getLong("id"),
					rs.getInt("version"), 
					rs.getLong("hand_id"), 
					rs.getLong("pokemon_id"), 
					energies,
					rs.getLong("base")));
		}
		return result;
	}
	
	private static List<Long> findAllEnergies(long pokemonId) throws SQLException{
		List<Long> energies = new ArrayList<Long>();
		ResultSet rs = PokemonEnergyTDG.findAll(pokemonId);
		while(rs.next()) {
			energies.add(rs.getLong("id"));
		}
		
		return energies;
	}
}
