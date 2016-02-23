package mindbadger.cah.game;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jdk.nashorn.internal.ir.SetSplitState;
import mindbadger.cah.action.Action;
import mindbadger.cah.sessions.PlayerSessions;

public class GameManagerTest {

	private static final String PLAYER1_NAME = "Mark";
	private static final String PLAYER2_NAME = "Anne";
	private static final String TYPE_1_GAME_URL = "Type 1 url";
	private static final String TYPE_1_DISPLAY_NAME = "Game Type 1";
	private static final String TYPE1_ID = "TYPE1";
	private static final String TYPE_2_GAME_URL = "Type 2 url";
	private static final String TYPE_2_DISPLAY_NAME = "Game Type 2";
	private static final String TYPE2_ID = "TYPE2";
	private static final GameType GAME_TYPE_1 = new GameType(TYPE1_ID,TYPE_1_DISPLAY_NAME,TYPE_1_GAME_URL);
	private static final GameType GAME_TYPE_2 = new GameType(TYPE2_ID,TYPE_2_DISPLAY_NAME,TYPE_2_GAME_URL);
	private static final Player PLAYER1 = new Player(PLAYER1_NAME);
	private static final Player PLAYER2 = new Player(PLAYER2_NAME);
	
	private GameManager objectUnderTest;
	
	@Mock
	PlayerSessions mockPlayerSessions;
	
	@Before
	public void init () {
		MockitoAnnotations.initMocks(this);

//		Map<String, GameType> gameTypes = new HashMap<String, GameType> ();
//		gameTypes.put(TYPE1_ID, GAME_TYPE_1);
//		gameTypes.put(TYPE2_ID, GAME_TYPE_2);
//		objectUnderTest.gameTypes = gameTypes;
//		objectUnderTest = new GameManager ();
		
		Set<GameType> gameTypes = new HashSet<GameType> ();
		gameTypes.add(GAME_TYPE_1);
		gameTypes.add(GAME_TYPE_2);
		objectUnderTest = new GameManager (gameTypes);
		
		when (mockPlayerSessions.getPlayer(PLAYER1_NAME)).thenReturn(PLAYER1);
		when (mockPlayerSessions.getPlayer(PLAYER2_NAME)).thenReturn(PLAYER2);
		objectUnderTest.players = mockPlayerSessions;
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

	@Test
	public void shouldCreateNewGameAndAddFirstPlayer () {
		// Given
		
		// When
		objectUnderTest.createNewGameAndAddFirstPlayer(TYPE1_ID, PLAYER1_NAME);
		
		// Then
		Map<Integer, Game> gamesReturned = objectUnderTest.getGames();
		List<Game> gamesOfType1 = objectUnderTest.getGamesForType(TYPE1_ID);
		List<Game> gamesOfType2 = objectUnderTest.getGamesForType(TYPE2_ID);

		assertEquals(1, gamesReturned.size());
		assertEquals (1, gamesOfType1.size());
		assertEquals (0, gamesOfType2.size());
		
		Game newGame = gamesOfType1.get(0);
		assertEquals (GAME_TYPE_1, newGame.getGameType());
		assertEquals (1, newGame.getGameId());
		assertEquals (1, newGame.getPlayers().size());
		assertEquals (PLAYER1, newGame.getPlayers().get(0));
		assertEquals(newGame, PLAYER1.getGame());
	}

	@Test
	public void shouldCreateSecondNewGameOfSameType () {
		// Given
		objectUnderTest.createNewGameAndAddFirstPlayer(TYPE1_ID, PLAYER1_NAME);
		
		// When
		objectUnderTest.createNewGameAndAddFirstPlayer(TYPE1_ID, PLAYER2_NAME);
		
		// Then
		Map<Integer, Game> gamesReturned = objectUnderTest.getGames();
		List<Game> gamesOfType1 = objectUnderTest.getGamesForType(TYPE1_ID);
		List<Game> gamesOfType2 = objectUnderTest.getGamesForType(TYPE2_ID);

		assertEquals(2, gamesReturned.size());
		assertEquals (2, gamesOfType1.size());
		assertEquals (0, gamesOfType2.size());

		Game firstNewGame = gamesOfType1.get(0);
		assertEquals (GAME_TYPE_1, firstNewGame.getGameType());
		assertEquals (1, firstNewGame.getGameId());
		assertEquals (1, firstNewGame.getPlayers().size());
		assertEquals (PLAYER1, firstNewGame.getPlayers().get(0));
		assertEquals(firstNewGame, PLAYER1.getGame());

		Game secondNewGame = gamesOfType1.get(1);
		assertEquals (GAME_TYPE_1, secondNewGame.getGameType());
		assertEquals (2, secondNewGame.getGameId());
		assertEquals (1, secondNewGame.getPlayers().size());
		assertEquals (PLAYER2, secondNewGame.getPlayers().get(0));
		assertEquals(secondNewGame, PLAYER2.getGame());
	}

	@Test
	public void shouldCreateSecondNewGameOfDifferentType () {
		// Given
		objectUnderTest.createNewGameAndAddFirstPlayer(TYPE1_ID, PLAYER1_NAME);
		
		// When
		objectUnderTest.createNewGameAndAddFirstPlayer(TYPE2_ID, PLAYER2_NAME);
		
		// Then
		Map<Integer, Game> gamesReturned = objectUnderTest.getGames();
		List<Game> gamesOfType1 = objectUnderTest.getGamesForType(TYPE1_ID);
		List<Game> gamesOfType2 = objectUnderTest.getGamesForType(TYPE2_ID);

		assertEquals(2, gamesReturned.size());
		assertEquals (1, gamesOfType1.size());
		assertEquals (1, gamesOfType2.size());
		
		Game firstNewGame = gamesOfType1.get(0);
		assertEquals (GAME_TYPE_1, firstNewGame.getGameType());
		assertEquals (1, firstNewGame.getGameId());
		assertEquals (1, firstNewGame.getPlayers().size());
		assertEquals (PLAYER1, firstNewGame.getPlayers().get(0));
		assertEquals(firstNewGame, PLAYER1.getGame());

		Game secondNewGame = gamesOfType2.get(0);
		assertEquals (GAME_TYPE_2, secondNewGame.getGameType());
		assertEquals (2, secondNewGame.getGameId());
		assertEquals (1, secondNewGame.getPlayers().size());
		assertEquals (PLAYER2, secondNewGame.getPlayers().get(0));
		assertEquals(secondNewGame, PLAYER2.getGame());
	}

}