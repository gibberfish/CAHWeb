package mindbadger.cah.game;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import mindbadger.cah.card.PackOfCards;
import mindbadger.cah.players.CardsAgainstHumanityPlayer;
import mindbadger.cah.players.Player;

public class CardsAgainstHumanityGameTest {
	private CardsAgainstHumanityGame objectUnderTest;

	@Mock
	private Player mockPlayer;
	
	private static final Integer GAME_ID = 123;
	private static final String TYPE_1_GAME_URL = "Type 1 url";
	private static final String TYPE_1_DISPLAY_NAME = "Game Type 1";
	private static final String TYPE1_ID = "TYPE1";
	private static final GameType GAME_TYPE = new GameType(TYPE1_ID,TYPE_1_DISPLAY_NAME,TYPE_1_GAME_URL);
	
	@Before
	public void init () {
		MockitoAnnotations.initMocks(this);
		objectUnderTest = new CardsAgainstHumanityGame (GAME_ID, GAME_TYPE);
	}
	
	@Test
	public void shouldHavePacksAlreadyInitialised () {
		// Given
		
		// When
		PackOfCards whitePack = objectUnderTest.getWhitePack ();
		PackOfCards blackPack = objectUnderTest.getBlackPack ();
		
		// Then
		assertNull (whitePack);
		assertNull (blackPack);
	}
	
	@Test
	public void shouldCreateGameSpecificPlayer () {
		// Given
		
		//When
		Player newPlayer = objectUnderTest.createGameSpecificPlayer(mockPlayer);
		
		// Then
		assertTrue (newPlayer instanceof CardsAgainstHumanityPlayer);
	}
}
