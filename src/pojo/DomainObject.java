package pojo;

import core.UoW;

public abstract class DomainObject {
	private long id;
	private int version;
	
	public DomainObject(long id, int version) {
		this.id = id;
		this.version = version;
	}
	
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public long getId() {
		return id;
	}

	public void markNew() {
		UoW.getCurrent().registerNew(this);
	}
	
	public void markClean() {
		UoW.getCurrent().registerClean(this);
	}
	
	protected void markDirty() {
		UoW.getCurrent().registerDirty(this);
	}
	
	protected void markDeleted() {
		UoW.getCurrent().registerDeleted(this);
	}
}
