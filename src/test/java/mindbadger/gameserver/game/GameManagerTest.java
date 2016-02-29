package mindbadger.gameserver.game;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import mindbadger.gameserver.game.Game;
import mindbadger.gameserver.game.GameFactory;
import mindbadger.gameserver.game.GameManager;
import mindbadger.gameserver.game.GameType;
import mindbadger.gameserver.player.Player;
import mindbadger.gameserver.player.PlayerInGame;
import mindbadger.gameserver.player.PlayerNotInGame;
import mindbadger.gameserver.player.PlayerSessions;

public class GameManagerTest {

	private static final String PLAYER1_NAME = "Mark";
	private static final String PLAYER2_NAME = "Anne";
	private static final String TYPE_1_GAME_URL = "Type 1 url";
	private static final String TYPE_1_DISPLAY_NAME = "Game Type 1";
	private static final String TYPE1_ID = "TYPE1";
	private static final String TYPE1_GAME_FACTORY = "TYPE1GameFactory";
	private static final String TYPE_2_GAME_URL = "Type 2 url";
	private static final String TYPE_2_DISPLAY_NAME = "Game Type 2";
	private static final String TYPE2_ID = "TYPE2";
	private static final String TYPE2_GAME_FACTORY = "TYPE2GameFactory";
	private static final String TYPE3_ID = "INVALIDTYPE";
	private static final GameType GAME_TYPE_1 = new GameType(TYPE1_ID,TYPE_1_DISPLAY_NAME,TYPE_1_GAME_URL);
	private static final GameType GAME_TYPE_2 = new GameType(TYPE2_ID,TYPE_2_DISPLAY_NAME,TYPE_2_GAME_URL);
	private static final Player PLAYER1 = new PlayerNotInGame(PLAYER1_NAME);
	private static final Player PLAYER2 = new PlayerNotInGame(PLAYER2_NAME);
	private static final Player PLAYER1INGAME = new PlayerInGame(PLAYER1) {
	};
	private static final Player PLAYER2INGAME = new PlayerInGame(PLAYER2) {
	};
	private static final Integer TYPE1_GAME_1_ID = 1;
	private static final Integer TYPE1_GAME_2_ID = 2;
	private static final Integer TYPE2_GAME_1_ID = 2;
	
	private GameManager objectUnderTest;
	
	@Mock
	private PlayerSessions mockPlayerSessions;
	@Mock
	private GameFactory mockGameFactory1;
	@Mock
	private GameFactory mockGameFactory2;
	@Mock
	private Game mockType1Game1;
	@Mock
	private Game mockType1Game2;
	@Mock
	private Game mockType2Game1;
	
	@Before
	public void init () {
		MockitoAnnotations.initMocks(this);

		when(mockGameFactory1.createGame(TYPE1_GAME_1_ID)).thenReturn(mockType1Game1);
		when(mockGameFactory1.createGame(TYPE1_GAME_2_ID)).thenReturn(mockType1Game2);
		when(mockGameFactory2.createGame(TYPE2_GAME_1_ID)).thenReturn(mockType2Game1);
		
		when(mockType1Game1.getGameId()).thenReturn(TYPE1_GAME_1_ID);
		when(mockType1Game2.getGameId()).thenReturn(TYPE1_GAME_2_ID);
		when(mockType2Game1.getGameId()).thenReturn(TYPE2_GAME_1_ID);

		when (mockPlayerSessions.getPlayer(PLAYER1_NAME)).thenReturn(PLAYER1);
		when (mockPlayerSessions.getPlayer(PLAYER2_NAME)).thenReturn(PLAYER2);
		
		Set<GameType> gameTypes = new HashSet<GameType> ();
		gameTypes.add(GAME_TYPE_1);
		gameTypes.add(GAME_TYPE_2);
		
		Map<String, GameFactory> gameFactorys = new HashMap<String, GameFactory> ();
		gameFactorys.put(TYPE1_GAME_FACTORY, mockGameFactory1);
		gameFactorys.put(TYPE2_GAME_FACTORY, mockGameFactory2);

		objectUnderTest = new GameManager (gameTypes);
		objectUnderTest.players = mockPlayerSessions;
		objectUnderTest.gameFactorys = gameFactorys;
		
		when (mockType1Game1.addPlayerToGame(PLAYER1)).thenReturn(PLAYER1INGAME);
		when (mockType1Game2.addPlayerToGame(PLAYER2)).thenReturn(PLAYER2INGAME);
		when (mockType2Game1.addPlayerToGame(PLAYER2)).thenReturn(PLAYER2INGAME);
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
		verify(mockGameFactory1,times(1)).createGame(TYPE1_GAME_1_ID);
		verify(mockGameFactory1,never()).createGame(TYPE1_GAME_2_ID);
		verify(mockGameFactory2,never()).createGame(TYPE2_GAME_1_ID);
		
		Map<Integer, Game> gamesReturned = objectUnderTest.getGames();
		List<Game> gamesOfType1 = objectUnderTest.getGamesForType(TYPE1_ID);
		List<Game> gamesOfType2 = objectUnderTest.getGamesForType(TYPE2_ID);

		assertEquals(1, gamesReturned.size());
		assertEquals (1, gamesOfType1.size());
		assertEquals (0, gamesOfType2.size());
		
		Game newGame = gamesOfType1.get(0);
		assertEquals (mockType1Game1, newGame);
		verify(mockType1Game1).addPlayerToGame(PLAYER1);
		assertEquals(newGame, PLAYER1.getGame());
		
		verify (mockPlayerSessions).replacePlayerNotInGameWithGameSpecificPlayer(PLAYER1INGAME);
	}

	@Test
	public void shouldCreateSecondNewGameOfSameType () {
		// Given
		objectUnderTest.createNewGameAndAddFirstPlayer(TYPE1_ID, PLAYER1_NAME);
		
		// When
		objectUnderTest.createNewGameAndAddFirstPlayer(TYPE1_ID, PLAYER2_NAME);
		
		// Then
		verify(mockGameFactory1,times(1)).createGame(TYPE1_GAME_1_ID);
		verify(mockGameFactory1,times(1)).createGame(TYPE1_GAME_2_ID);
		verify(mockGameFactory2,never()).createGame(TYPE2_GAME_1_ID);
			
		Map<Integer, Game> gamesReturned = objectUnderTest.getGames();
		List<Game> gamesOfType1 = objectUnderTest.getGamesForType(TYPE1_ID);
		List<Game> gamesOfType2 = objectUnderTest.getGamesForType(TYPE2_ID);

		assertEquals(2, gamesReturned.size());
		assertEquals (2, gamesOfType1.size());
		assertEquals (0, gamesOfType2.size());

		Game firstNewGame = gamesOfType1.get(0);
		assertEquals (mockType1Game1, firstNewGame);
		verify(mockType1Game1).addPlayerToGame(PLAYER1);
		assertEquals(firstNewGame, PLAYER1.getGame());

		Game secondNewGame = gamesOfType1.get(1);
		assertEquals (mockType1Game2, secondNewGame);
		verify(mockType1Game2).addPlayerToGame(PLAYER2);
		assertEquals(secondNewGame, PLAYER2.getGame());
		
		verify (mockPlayerSessions).replacePlayerNotInGameWithGameSpecificPlayer(PLAYER1INGAME);
		verify (mockPlayerSessions).replacePlayerNotInGameWithGameSpecificPlayer(PLAYER2INGAME);
	}

	@Test
	public void shouldCreateSecondNewGameOfDifferentType () {
		// Given
		objectUnderTest.createNewGameAndAddFirstPlayer(TYPE1_ID, PLAYER1_NAME);
		
		// When
		objectUnderTest.createNewGameAndAddFirstPlayer(TYPE2_ID, PLAYER2_NAME);
		
		// Then
		verify(mockGameFactory1,times(1)).createGame(TYPE1_GAME_1_ID);
		verify(mockGameFactory1,never()).createGame(TYPE1_GAME_2_ID);
		verify(mockGameFactory2,times(1)).createGame(TYPE2_GAME_1_ID);

		Map<Integer, Game> gamesReturned = objectUnderTest.getGames();
		List<Game> gamesOfType1 = objectUnderTest.getGamesForType(TYPE1_ID);
		List<Game> gamesOfType2 = objectUnderTest.getGamesForType(TYPE2_ID);

		assertEquals(2, gamesReturned.size());
		assertEquals (1, gamesOfType1.size());
		assertEquals (1, gamesOfType2.size());
		
		Game firstNewGame = gamesOfType1.get(0);
		assertEquals (mockType1Game1, firstNewGame);
		verify(mockType1Game1).addPlayerToGame(PLAYER1);
		assertEquals(firstNewGame, PLAYER1.getGame());

		Game secondNewGame = gamesOfType2.get(0);
		assertEquals (mockType2Game1, secondNewGame);
		verify(mockType2Game1).addPlayerToGame(PLAYER2);
		assertEquals(secondNewGame, PLAYER2.getGame());
		
		verify (mockPlayerSessions).replacePlayerNotInGameWithGameSpecificPlayer(PLAYER1INGAME);
		verify (mockPlayerSessions).replacePlayerNotInGameWithGameSpecificPlayer(PLAYER2INGAME);
	}

	@Test
	public void shouldNotCreateNewGameIfInvalidTypeSupplied () {
		// Given
		
		// When
		objectUnderTest.createNewGameAndAddFirstPlayer(TYPE3_ID, PLAYER2_NAME);
		
		// Then
		Map<Integer, Game> gamesReturned = objectUnderTest.getGames();
		List<Game> gamesOfType1 = objectUnderTest.getGamesForType(TYPE3_ID);

		assertEquals(0, gamesReturned.size());
		assertEquals (0, gamesOfType1.size());
	}
}
