package pojo;

public class Card {
	private long id;
	private char type;
	private String name;
	
	public Card(long id,char type, String name) {
		super();
		this.id = id;
		this.type = type;
		this.name = name;
	}

	public char getType() {
		return type;
	}
	
	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}	
}
