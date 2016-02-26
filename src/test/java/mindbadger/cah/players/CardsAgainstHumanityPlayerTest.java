package mindbadger.cah.players;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import mindbadger.cah.card.BlackCard;
import mindbadger.cah.card.WhiteCard;

public class CardsAgainstHumanityPlayerTest {
	CardsAgainstHumanityPlayer objectUnderTest;

	private static final String PLAYER1 = "PLAYER1";
	private Player playerNotInGame = new PlayerNotInGame(PLAYER1);
	
	@Before
	public void init () {
		objectUnderTest = new CardsAgainstHumanityPlayer(playerNotInGame);
	}
	
	@Test
	public void shouldCreatePlayer () {
		// Given
		
		// When
		List<WhiteCard> playersHand = objectUnderTest.getHand ();
		List<BlackCard> blackCardsWon = objectUnderTest.getCardsWon ();
		
		// Then
		assertEquals (0, playersHand.size());
		assertEquals (0, blackCardsWon.size());
	}
	
	@Test
	public void shouldAddABlackCardToCardsWon () {
		// Given
		BlackCard blackCard = new BlackCard ("Black card 1");
		
		// When
		objectUnderTest.addBlackCardToCardsWon (blackCard);
		
		// Then
		List<BlackCard> blackCardsWon = objectUnderTest.getCardsWon();
		assertEquals (1, blackCardsWon.size());
		assertEquals (blackCard, blackCardsWon.get(0));
	}

	@Test
	public void shouldAddAWhiteCardToPlayersHand () {
		// Given
		WhiteCard whiteCard = new WhiteCard ("White card 1");
		
		// When
		objectUnderTest.dealWhiteCardToPlayer (whiteCard);
		
		// Then
		List<WhiteCard> hand = objectUnderTest.getHand();
		assertEquals (1, hand.size());
		assertEquals (whiteCard, hand.get(0));
	}
	
	@Test
	public void shouldThrowAnErrorWhenAttemptToDrawBlackCardWhenNoneExist () {
		// Given
		
		// When
		try {
			objectUnderTest.drawCardFromCardsWon ();
			fail("There are no cards won at this point, so we can't draw one");
		} catch (IllegalStateException e) {
			// Then
			assertEquals ("There are no black cards to draw from", e.getMessage());
		}
	}
	
	@Test
	public void shouldDrawBlackCard () {
		// Given
		BlackCard blackCard1 = new BlackCard ("Black card 1");
		BlackCard blackCard2 = new BlackCard ("Black card 2");
		BlackCard blackCard3 = new BlackCard ("Black card 3");
		objectUnderTest.addBlackCardToCardsWon (blackCard1);
		objectUnderTest.addBlackCardToCardsWon (blackCard2);
		objectUnderTest.addBlackCardToCardsWon (blackCard3);
		
		// When
		BlackCard cardDrawn = objectUnderTest.drawCardFromCardsWon ();
		
		// Then
		assertEquals (blackCard1,cardDrawn);
		
		List<BlackCard> blackCardsWon = objectUnderTest.getCardsWon();
		assertEquals (2, blackCardsWon.size());
		assertEquals (blackCard2, blackCardsWon.get(0));
		assertEquals (blackCard3, blackCardsWon.get(1));
	}

	//TODO Implement test
	@Ignore
	@Test
	public void shouldThrowAnErrorWhenAttemptToDrawWhiteCardWhenNoneExist () {
		// Given
		
		// When
		try {
			//objectUnderTest.drawCardFromCardsWon ();
			fail("There are no cards in the player's hand at this point, so we can't draw one");
		} catch (IllegalStateException e) {
			// Then
			assertEquals ("There are no cards in your hand to draw from", e.getMessage());
		}
	}

	//TODO Implement test
	@Ignore
	@Test
	public void shouldDrawWhiteCard () {
		// Given
//		BlackCard blackCard1 = new BlackCard ("Black card 1");
//		BlackCard blackCard2 = new BlackCard ("Black card 2");
//		BlackCard blackCard3 = new BlackCard ("Black card 3");
//		objectUnderTest.addBlackCardToCardsWon (blackCard1);
//		objectUnderTest.addBlackCardToCardsWon (blackCard2);
//		objectUnderTest.addBlackCardToCardsWon (blackCard3);
//		
//		// When
//		BlackCard cardDrawn = objectUnderTest.drawCardFromCardsWon ();
//		
//		// Then
//		assertEquals (blackCard1,cardDrawn);
//		
//		List<BlackCard> blackCardsWon = objectUnderTest.getCardsWon();
//		assertEquals (2, blackCardsWon.size());
//		assertEquals (blackCard2, blackCardsWon.get(0));
//		assertEquals (blackCard3, blackCardsWon.get(1));
	}

}
