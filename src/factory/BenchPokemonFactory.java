package factory;

import java.util.List;

import pojo.BenchPokemon;
import util.IdGenerator;

public abstract class BenchPokemonFactory {
	public static BenchPokemon createNew(long handId, long pokemonId, List<Long> energies) {
		BenchPokemon b = new BenchPokemon(
				IdGenerator.getInstance().createID(),
				1,
				handId,
				pokemonId,
				energies,
				-1);
		b.markNew();
		return b;
	}
	
	public static BenchPokemon createClean(long id, int version, long handId, long pokemonId, List<Long> energies, long base) {
		BenchPokemon b = new BenchPokemon(
				id,
				version,
				handId,
				pokemonId,
				energies,
				base);
		b.markClean();
		return b;
	}
}
