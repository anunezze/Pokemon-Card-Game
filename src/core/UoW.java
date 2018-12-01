package core;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import OutputMapper.BenchPokemonOutputMapper;
import OutputMapper.ChallengeOutputMapper;
import OutputMapper.DeckOutputMapper;
import OutputMapper.DiscardCardOuputMapper;
import OutputMapper.GameOutputMapper;
import OutputMapper.HandOutputMapper;
import OutputMapper.UserOutputMapper;
import pojo.BenchPokemon;
import pojo.Challenge;
import pojo.Deck;
import pojo.DiscardCard;
import pojo.DomainObject;
import pojo.Game;
import pojo.Hand;
import pojo.User;
import util.LostUpdateException;

public class UoW {
	
	private List<DomainObject> newObjects;
	private List<DomainObject> dirtyObjects; 
	private List<DomainObject> deletedObjects ;
	private Map<Long, DomainObject> cleanObjects;
	
	private static ThreadLocal<UoW> current = new ThreadLocal<UoW>();
	
	private UoW() {
		this.newObjects = new ArrayList<DomainObject>();
		this.dirtyObjects  = new ArrayList<DomainObject>();
		this.deletedObjects= new ArrayList<DomainObject>();
		this.cleanObjects = new HashMap<Long, DomainObject>();
	}
	
	public void registerClean(DomainObject obj) {
		if(!cleanObjects.containsKey(obj.getId())) {
			cleanObjects.put(obj.getId(), obj);
		}
	}
	
	public Map<Long, DomainObject> getCleanObjects(){
		return this.cleanObjects;
	}
	
	public List<DomainObject> getNewObjects(){
		return this.newObjects;
	}
	
	public void registerDirty(DomainObject obj) {
		if(obj == null || deletedObjects.contains(obj)) {
			return;
		}
		if(!dirtyObjects.contains(obj) && !newObjects.contains(obj)) {
			dirtyObjects.add(obj);
		}
	}
	
	public void registerNew(DomainObject obj) {
		if(obj == null || dirtyObjects.contains(obj) || deletedObjects.contains(obj)|| newObjects.contains(obj)) {
			return;
		}
		this.newObjects.add(obj);
	}
	
	public void registerDeleted(DomainObject obj) {
		if(obj == null || newObjects.remove(obj)) {
			return;
		}
		dirtyObjects.remove(obj);
		if(!deletedObjects.contains(obj)) {
			deletedObjects.add(obj);
		}
	}
	
	public void commit() throws SQLException, LostUpdateException {
		insertNew();
		updateDirty();
		deleteDeleted();
	}
	
	public static UoW getCurrent() {
		return (UoW) current.get();
	}
	
	public static void newUoW() {
		current.set(new UoW());
	}
	
	private void insertNew() throws SQLException {
		for(Iterator objects = newObjects.iterator(); objects.hasNext();) {
			DomainObject obj = (DomainObject) objects.next();
			if(obj.getClass() == User.class) {
				UserOutputMapper.insert((User)obj);
			}
			else if(obj.getClass() == Deck.class) {
				DeckOutputMapper.insert((Deck) obj);
			}
			else if(obj.getClass() == Challenge.class){
				ChallengeOutputMapper.insert((Challenge)obj);
			}
			else if(obj.getClass() == Game.class) {
				GameOutputMapper.insert((Game) obj);
			}
			else if(obj.getClass() == Hand.class) {
				HandOutputMapper.insert((Hand)obj);
			}
			else if(obj.getClass() == BenchPokemon.class) {
				BenchPokemonOutputMapper.insert((BenchPokemon)obj);
			}
			else if(obj.getClass() == DiscardCard.class) {
				DiscardCardOuputMapper.insert((DiscardCard)obj);
			}
		}
	}
	private void updateDirty() throws SQLException, LostUpdateException {
		for(Iterator objects = dirtyObjects.iterator(); objects.hasNext();) {
			DomainObject obj = (DomainObject) objects.next();
			if(obj.getClass() == Challenge.class) {
				ChallengeOutputMapper.update((Challenge) obj);
			}
			else if(obj.getClass() == Hand.class) {
				HandOutputMapper.update((Hand)obj);
			}
			else if(obj.getClass() == Game.class) {
				GameOutputMapper.update((Game)obj);
			}
			else if(obj.getClass() == BenchPokemon.class) {
				BenchPokemonOutputMapper.update((BenchPokemon)obj);
			}
		}
		
	}
	private void deleteDeleted() {
		
	}
}
