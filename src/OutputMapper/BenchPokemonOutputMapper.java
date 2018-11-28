package OutputMapper;

import java.sql.SQLException;

import pojo.BenchPokemon;
import tdg.BenchTDG;
import tdg.PokemonEnergyTDG;

public abstract class BenchPokemonOutputMapper {
	
	public static void insert(BenchPokemon b) throws SQLException {
		BenchTDG.insert(
				b.getId(), 
				b.getVersion(), 
				b.getHandId(), 
				b.getPokemonId(), 
				!b.getEnergies().isEmpty(), 
				b.getBase());
		for(int i = 0; i< b.getEnergies().size(); i++) {
			PokemonEnergyTDG.insert(b.getPokemonId(), b.getEnergies().get(0));
		}
	}
}
