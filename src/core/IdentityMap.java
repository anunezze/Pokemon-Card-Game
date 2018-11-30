package core;


import pojo.DomainObject;

public abstract class IdentityMap {
	
	public static DomainObject find(long id) {
		DomainObject result = null;
		if(UoW.getCurrent().getCleanObjects().containsKey(id)) {
			return UoW.getCurrent().getCleanObjects().get(id);
		}
		for(int i = 0; i < UoW.getCurrent().getNewObjects().size(); i++) {
			if(UoW.getCurrent().getNewObjects().get(i).getId() == id) {
				return UoW.getCurrent().getNewObjects().get(i);
			}
		}
			
		return result;
	}
	
	public static void add(DomainObject obj) {
		UoW.getCurrent().getCleanObjects().put(obj.getId(), obj);
	}
	
	public static boolean contains(long id) {
		boolean contains = UoW.getCurrent().getCleanObjects().containsKey(id);
		for(int i = 0;!contains && i< UoW.getCurrent().getNewObjects().size();i ++) {
			if(UoW.getCurrent().getNewObjects().get(i).getId() == id) {
				contains = true;
			}
		}
		return contains;
	}
}
