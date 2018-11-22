package pojo;

import core.UoW;

public abstract class DomainObject {
	private long id;
	private int version;
	
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public long getId() {
		return id;
	}

	protected void markNew() {
		UoW.getCurrent().registerNew();
	}
	
	protected void markClean() {
		UoW.getCurrent().registerClean();
	}
	
	protected void markDirty() {
		UoW.getCurrent().registerDirty();
	}
	
	protected void markDeleted() {
		UoW.getCurrent().registerDeleted();
	}
}
