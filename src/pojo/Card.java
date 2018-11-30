package pojo;

public class Card{
	private long id;
	private char type;
	private String name;
	private String base;
	
	public Card(long id, char type, String name){
		this.id = id;
		this.type = type;
		this.name = name;
	}
	public Card(long id, char type, String name, String base) {
		this(id,type,name);
		this.base = base;
	}
	public long getId() {
		return id;
	}

	public char getType() {
		return type;
	}

	public String getName() {
		return name;
	}
	public String getBase() {
		return base;
	}	
}
