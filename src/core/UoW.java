package core;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import OutputMapper.ChallengeOutputMapper;
import OutputMapper.DeckOutputMapper;
import OutputMapper.GameOutputMapper;
import OutputMapper.HandOutputMapper;
import OutputMapper.UserOutputMapper;
import pojo.Challenge;
import pojo.Deck;
import pojo.DomainObject;
import pojo.Game;
import pojo.Hand;
import pojo.IDeck;
import pojo.User;
import util.LostUpdateException;

public class UoW {
	
	private List<DomainObject> newObjects = new ArrayList<DomainObject>();
	private List<DomainObject> dirtyObjects = new ArrayList<DomainObject>(); 
	private List<DomainObject> deletedObjects = new ArrayList<DomainObject>();
	
	private static ThreadLocal<UoW> current = new ThreadLocal<UoW>();
	
	private UoW() {
		
	}
	
	public void registerClean(DomainObject obj) {
		
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
		}
	}
	private void updateDirty() throws SQLException, LostUpdateException {
		for(Iterator objects = dirtyObjects.iterator(); objects.hasNext();) {
			DomainObject obj = (DomainObject) objects.next();
			if(obj.getClass() == Challenge.class) {
				ChallengeOutputMapper.update((Challenge) obj);
			}
		}
		
	}
	private void deleteDeleted() {
		
	}
	
}
