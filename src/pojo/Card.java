package pojo;

public class Card extends DomainObject{
	private char type;
	private String name;
	
	public Card(long id,int version, char type, String name){
		super(id, version);
		this.type = type;
		this.name = name;
	}

	public char getType() {
		return type;
	}

	public String getName() {
		return name;
	}	
}
