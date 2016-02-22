package mindbadger.cah.game;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import mindbadger.cah.action.Action;

public class GameManagerTest {

	private static final String PLAYER_NAME = "Mark";
	private static final String TYPE_1_GAME_URL = "Type 1 url";
	private static final String TYPE_1_DISPLAY_NAME = "Game Type 1";
	private static final String TYPE1_ID = "TYPE1";
	private static final String TYPE_2_GAME_URL = "Type 2 url";
	private static final String TYPE_2_DISPLAY_NAME = "Game Type 2";
	private static final String TYPE2_ID = "TYPE2";
	private static final GameType GAME_TYPE_1 = new GameType(TYPE1_ID,TYPE_1_DISPLAY_NAME,TYPE_1_GAME_URL);
	private static final GameType GAME_TYPE_2 = new GameType(TYPE2_ID,TYPE_2_DISPLAY_NAME,TYPE_2_GAME_URL);
	
	private GameManager objectUnderTest;
	
	@Before
	public void init () {
		MockitoAnnotations.initMocks(this);
		
		objectUnderTest = new GameManager ();

		Map<String, GameType> gameTypes = new HashMap<String, GameType> ();
		gameTypes.put(TYPE1_ID, GAME_TYPE_1);
		gameTypes.put(TYPE2_ID, GAME_TYPE_2);
		objectUnderTest.gameTypes = gameTypes;
	}
	
	@Test
	public void canGetGamesForType () {
		// Given
		// (the game types added in the init method above)
		
		// When
		List<GameType> gameTypesReturned = objectUnderTest.getGameTypes();
		
		// Then
		assertEquals(2, gameTypesReturned.size());
		assertTrue (gameTypesReturned.contains(GAME_TYPE_1));
		assertTrue (gameTypesReturned.contains(GAME_TYPE_2));
	}
	
	@Test
	public void noGamesExistWhenObjectCreated () {
		// Given
		// (object created, but no games created)
		
		// When
		Map<Integer, Game> gamesReturned = objectUnderTest.getGames();
		List<Game> gamesOfType1 = objectUnderTest.getGamesForType(TYPE1_ID);
		List<Game> gamesOfType2 = objectUnderTest.getGamesForType(TYPE2_ID);
		
		// Then
		assertEquals(0, gamesReturned.size());
		assertEquals (0, gamesOfType1.size());
		assertEquals (0, gamesOfType2.size());
	}

	@Ignore
	@Test
	public void shouldCreateNewGameAndAddFirstPlayer () {
		// Given
		
		// When
		objectUnderTest.createNewGameAndAddFirstPlayer(TYPE1_ID, PLAYER_NAME);
		
		// Then
		Map<Integer, Game> gamesReturned = objectUnderTest.getGames();
		List<Game> gamesOfType1 = objectUnderTest.getGamesForType(TYPE1_ID);
		List<Game> gamesOfType2 = objectUnderTest.getGamesForType(TYPE2_ID);

		assertEquals(1, gamesReturned.size());
		assertEquals (1, gamesOfType1.size());
		assertEquals (0, gamesOfType2.size());
	}
	
}
