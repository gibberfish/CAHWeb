package mindbadger.cah.game;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import mindbadger.cah.card.BlackPack;
import mindbadger.cah.card.WhitePack;

public class CardsAgainstHumanityGameTest {
	private CardsAgainstHumanityGame objectUnderTest;
	
	private static final Integer GAME_ID = 123;
	private static final String TYPE_1_GAME_URL = "Type 1 url";
	private static final String TYPE_1_DISPLAY_NAME = "Game Type 1";
	private static final String TYPE1_ID = "TYPE1";
	private static final String TYPE1_CLASS = "CardsAgainstHumanityGame";
	private static final GameType GAME_TYPE = new GameType(TYPE1_ID,TYPE_1_DISPLAY_NAME,TYPE_1_GAME_URL,TYPE1_CLASS);
	
	@Before
	public void init () {
		objectUnderTest = new CardsAgainstHumanityGame (GAME_ID, GAME_TYPE);
	}
	
	//TODO Need to find some way of injecting a pack, maybe using a factory of some type
	@Ignore
	@Test
	public void shouldHavePacksAlreadyInitialised () {
		// Given
		
		// When
		WhitePack whitePack = objectUnderTest.getWhitePack ();
		BlackPack blackPack = objectUnderTest.getBlackPack ();
		
		// Then
		assertEquals (1, whitePack.getPack().size());
		assertEquals (1, blackPack.getPack().size());
	}
}
