package pojo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class HandTest {

	@Test
	void test() {
		String TEST_DECK1 =
				"e \"Fire\"\n" +
				"e \"Fire\"\n" +
				"p \"Charizard\"\n" +
				"e \"Fire\"\n" +
				"e \"Fire\"\n" +
				"e \"Fire\"\n" +
				"p \"Charizard\"\n" +
				"p \"Meowth\"\n" +
				"e \"Fire\"\n" +
				"t \"Misty\"\n" +
				"t \"Misty\"\n" +
				"e \"Fire\"\n" +
				"e \"Fire\"\n" +
				"e \"Fire\"\n" +
				"p \"Charizard\"\n" +
				"e \"Fire\"\n" +
				"e \"Fire\"\n" +
				"e \"Fire\"\n" +
				"p \"Charizard\"\n" +
				"p \"Meowth\"\n" +
				"e \"Fire\"\n" +
				"t \"Misty\"\n" +
				"t \"Misty\"\n" +
				"e \"Fire\"\n" +
				"e \"Fire\"\n" +
				"e \"Fire\"\n" +
				"p \"Raichu\" \"Pikachu\"\n" +
				"e \"Fire\"\n" +
				"e \"Fire\"\n" +
				"e \"Fire\"\n" +
				"p \"Charizard\"\n" +
				"p \"Meowth\"\n" +
				"e \"Fire\"\n" +
				"t \"Misty\"\n" +
				"t \"Misty\"\n" +
				"e \"Fire\"\n" +
				"e \"Fire\"\n" +
				"e \"Fire\"\n" +
				"p \"Charizard\"\n" +
				"e \"Fire\"\n";
		Deck deck = new Deck(1,2,3,TEST_DECK1);
		for(int i = 0; i < deck.getCards().size(); i++) {
			System.out.println(deck.getCards().get(i).getBase());
		}
	}
}
