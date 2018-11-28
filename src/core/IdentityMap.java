package core;

import java.util.Map;

import pojo.DomainObject;

public abstract class IdentityMap {
	
	public static DomainObject find(long id) {
		return UoW.getCurrent().getCleanObjects().get(id);
	}
	
	public static void add(DomainObject obj) {
		UoW.getCurrent().getCleanObjects().put(obj.getId(), obj);
	}
	
	public static boolean contains(long id) {
		
		boolean contains = UoW.getCurrent().getCleanObjects().containsKey(id);
		return contains;
	}
}
