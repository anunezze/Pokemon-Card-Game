package core;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import OutputMapper.UserOutputMapper;
import pojo.DomainObject;
import pojo.User;

public class UoW {
	
	private List<DomainObject> newObjects = new ArrayList<DomainObject>();
	private List<DomainObject> dirtyObjects = new ArrayList<DomainObject>(); 
	private List<DomainObject> deletedObjects = new ArrayList<DomainObject>();
	
	private Map mapperDictionary = new HashMap();
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
	
	public void commit() throws SQLException {
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
		}
	}
	private void updateDirty() {
		
	}
	private void deleteDeleted() {
		
	}
	
}
