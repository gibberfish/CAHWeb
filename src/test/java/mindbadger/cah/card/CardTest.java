package mindbadger.cah.card;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CardTest {
	
	private static final CardType CARD_TYPE = CardType.BLACK;
	private static final String CARD_TEXT = "hello {}. I am {}.";

	@Test
	public void shouldGetDetailsAfterCardCreated () {
		// Given
		
		// When
		Card card = new Card (CARD_TYPE, CARD_TEXT);
		
		// Then
		assertEquals(CARD_TYPE, card.getType());
		assertEquals(CARD_TEXT, card.getWords());
	}
	
	@Test
	public void shouldCountTokens () {
		// Given
		
		// When
		Card card = new Card (CARD_TYPE, CARD_TEXT);
		
		// Then
		assertEquals(2, card.getTokens());
	}

	@Test
	public void shouldProvideBackOfCardWords () {
		// Given
		
		// When
		Card card = new Card (CARD_TYPE, CARD_TEXT);
		
		// Then
		assertEquals("hello _________. I am _________.", card.getBackOfCardWords());
	}

}
