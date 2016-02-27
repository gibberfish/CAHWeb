package mindbadger.cah.card;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class AbstractPackTest {
	private PackOfCards objectUnderTest;
	
	@Before
	public void init () {
		List<Card> pack = new ArrayList<Card> ();
		pack.add(new Card(CardType.WHITE, "WHITE_CARD_1"));
		pack.add(new Card(CardType.WHITE, "WHITE_CARD_2"));
		pack.add(new Card(CardType.WHITE, "WHITE_CARD_3"));
		pack.add(new Card(CardType.WHITE, "WHITE_CARD_4"));
		pack.add(new Card(CardType.WHITE, "WHITE_CARD_5"));
		pack.add(new Card(CardType.WHITE, "WHITE_CARD_6"));
		pack.add(new Card(CardType.WHITE, "WHITE_CARD_7"));
		pack.add(new Card(CardType.WHITE, "WHITE_CARD_8"));
		
		objectUnderTest = new PackOfCards(pack);
	}
	
	@Test
	public void packShouldContainCardsWhenCreated () {
		// Given
		// When
		// Then
		assertEquals (8, objectUnderTest.getPack().size());
	}
	
	@Test
	public void shouldShufflePack () {
		// Given
		boolean initialOrderIsCorrect =
				("WHITE_CARD_1".equals (objectUnderTest.getPack().get(0).getWords())) &&
				("WHITE_CARD_2".equals (objectUnderTest.getPack().get(1).getWords())) &&
				("WHITE_CARD_3".equals (objectUnderTest.getPack().get(2).getWords())) &&
				("WHITE_CARD_4".equals (objectUnderTest.getPack().get(3).getWords())) &&
				("WHITE_CARD_5".equals (objectUnderTest.getPack().get(4).getWords())) &&
				("WHITE_CARD_6".equals (objectUnderTest.getPack().get(5).getWords())) &&
				("WHITE_CARD_7".equals (objectUnderTest.getPack().get(6).getWords())) &&
				("WHITE_CARD_8".equals (objectUnderTest.getPack().get(7).getWords()));
		
		// When
		objectUnderTest.shuffle ();
		
		// Then
		boolean isTheSameOrderAsBeforeTheShuffle = 
				("WHITE_CARD_1".equals (objectUnderTest.getPack().get(0).getWords())) &&
				("WHITE_CARD_2".equals (objectUnderTest.getPack().get(1).getWords())) &&
				("WHITE_CARD_3".equals (objectUnderTest.getPack().get(2).getWords())) &&
				("WHITE_CARD_4".equals (objectUnderTest.getPack().get(3).getWords())) &&
				("WHITE_CARD_5".equals (objectUnderTest.getPack().get(4).getWords())) &&
				("WHITE_CARD_6".equals (objectUnderTest.getPack().get(5).getWords())) &&
				("WHITE_CARD_7".equals (objectUnderTest.getPack().get(6).getWords())) &&
				("WHITE_CARD_8".equals (objectUnderTest.getPack().get(7).getWords()));
		
		assertTrue (initialOrderIsCorrect);
		assertFalse (isTheSameOrderAsBeforeTheShuffle);
	}
	
	@Test
	public void shouldDealACard () {
		// Given
		
		// When
		Card dealtCard = objectUnderTest.dealCard();
		
		// Then
		assertEquals ("WHITE_CARD_1", dealtCard.getWords());
		assertEquals (7, objectUnderTest.getPack().size());
		assertFalse (objectUnderTest.getPack().contains(dealtCard));
	}
	
	@Test
	public void shouldReturnACardToTheBottomOfThePack () {
		// Given
		Card newCard = new Card(CardType.WHITE, "WHITE_CARD_9");
		
		// When
		objectUnderTest.returnCard (newCard);
		
		// Then
		assertEquals (9, objectUnderTest.getPack().size());
		assertEquals (newCard, objectUnderTest.getPack().get(8));
	}
}
