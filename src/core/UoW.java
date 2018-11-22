package core;

import java.util.ArrayList;
import java.util.List;

public class UoW {
	
	private List newObjects = new ArrayList();
	private List dirtyObjects = new ArrayList(); 
	private List removedObjects = new ArrayList();
	
	private static ThreadLocal current;
	
	public void registerClean() {
		
	}
	
	public void registerDirty() {
		
	}
	
	public void registerNew() {
		
	}
	
	public void registerDeleted() {
		
	}
	
	public static UoW getCurrent() {
		return (UoW) current.get();
	}
	
	public static void setCurrent(UoW uow) {
		current.set(uow);
	}
	
	
}
