package pojo;

import core.UoW;

public interface IDomainObject {
	
	public int getVersion();

	public void setVersion(int version);

	public long getId();

	public void markNew();
	
	public void markClean();
	
	public void markDirty();
	
	public void markDeleted();
}
