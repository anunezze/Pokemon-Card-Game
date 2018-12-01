package pojo;

import java.util.List;

public class BenchPokemon extends DomainObject {
	private long handId;
	private long pokemonId;
	private List<Long> energies;
	private long base;
	
	public BenchPokemon(long id, int version, long handId, long pokemonId, List<Long> energies, long base) {
		super(id, version);
		this.handId = handId;
		this.pokemonId = pokemonId;
		this.energies = energies;
		this.base = base;
	}

	public long getHandId() {
		return handId;
	}

	public void setHandId(long handId) {
		this.handId = handId;
	}

	public long getPokemonId() {
		return pokemonId;
	}

	public void setPokemonId(long pokemonId) {
		this.pokemonId = pokemonId;
	}

	public List<Long> getEnergies() {
		return energies;
	}

	public long getBase() {
		return base;
	}

	public void setBase(long base) {
		this.base = base;
		this.markDirty();
	}
	public void addEnergy(long id) {
		this.energies.add(id);
		this.markDirty();
	}
}
