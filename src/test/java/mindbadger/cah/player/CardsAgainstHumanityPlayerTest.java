package mindbadger.cah.player;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import mindbadger.cah.card.Card;
import mindbadger.cah.card.CardType;
import mindbadger.cah.player.CardsAgainstHumanityPlayer;
import mindbadger.gameserver.player.Player;
import mindbadger.gameserver.player.PlayerNotInGame;

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
		List<Card> playersHand = objectUnderTest.getHand ();
		List<Card> CardsWon = objectUnderTest.getCardsWon ();
		
		// Then
		assertEquals (0, playersHand.size());
		assertEquals (0, CardsWon.size());
	}
	
	@Test
	public void shouldAddACardToCardsWon () {
		// Given
		Card Card = new Card (CardType.BLACK, "Black card 1");
		
		// When
		objectUnderTest.addCardToCardsWon (Card);
		
		// Then
		List<Card> CardsWon = objectUnderTest.getCardsWon();
		assertEquals (1, CardsWon.size());
		assertEquals (Card, CardsWon.get(0));
	}

	@Test
	public void shouldAddACardToPlayersHand () {
		// Given
		Card Card = new Card (CardType.WHITE, "White card 1");
		
		// When
		objectUnderTest.dealCardToPlayer (Card);
		
		// Then
		List<Card> hand = objectUnderTest.getHand();
		assertEquals (1, hand.size());
		assertEquals (Card, hand.get(0));
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
		Card Card1 = new Card (CardType.BLACK, "Black card 1");
		Card Card2 = new Card (CardType.BLACK, "Black card 2");
		Card Card3 = new Card (CardType.BLACK, "Black card 3");
		objectUnderTest.addCardToCardsWon (Card1);
		objectUnderTest.addCardToCardsWon (Card2);
		objectUnderTest.addCardToCardsWon (Card3);
		
		// When
		Card cardDrawn = objectUnderTest.drawCardFromCardsWon ();
		
		// Then
		assertEquals (Card1,cardDrawn);
		
		List<Card> CardsWon = objectUnderTest.getCardsWon();
		assertEquals (2, CardsWon.size());
		assertEquals (Card2, CardsWon.get(0));
		assertEquals (Card3, CardsWon.get(1));
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
//		Card Card1 = new Card ("Black card 1");
//		Card Card2 = new Card ("Black card 2");
//		Card Card3 = new Card ("Black card 3");
//		objectUnderTest.addCardToCardsWon (Card1);
//		objectUnderTest.addCardToCardsWon (Card2);
//		objectUnderTest.addCardToCardsWon (Card3);
//		
//		// When
//		Card cardDrawn = objectUnderTest.drawCardFromCardsWon ();
//		
//		// Then
//		assertEquals (Card1,cardDrawn);
//		
//		List<Card> CardsWon = objectUnderTest.getCardsWon();
//		assertEquals (2, CardsWon.size());
//		assertEquals (Card2, CardsWon.get(0));
//		assertEquals (Card3, CardsWon.get(1));
	}

}
