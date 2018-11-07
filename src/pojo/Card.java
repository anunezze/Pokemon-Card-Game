package pojo;

public class Card {
	private char type;
	private String name;
	
	public Card(char type, String name) {
		super();
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
