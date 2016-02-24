package mindbadger.cah.action;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import mindbadger.cah.game.Game;
import mindbadger.cah.players.Player;
import mindbadger.cah.players.PlayerSessions;
import mindbadger.cah.websocket.GameStateChange;

public class ActionTest {
	private static final String PLAYER_NAME = "PLAYER1";
	private static final String SESSION_ID = "Session1";
	private static final Integer GAME_ID = 1234;

	private Action objectUnderTest;
	
	private GameStateChange gameStateChange = new GameStateChange();
	
	@Mock
	private PlayerAction mockPlayerAction;
	
	@Mock
	private PlayerSessions mockSessions;

	@Mock
	private Player mockPlayer;
	
	@Mock
	private Game mockGame;

	@Before
	public void init () {
		MockitoAnnotations.initMocks(this);
		
		objectUnderTest = new Action () {
			@Override
			protected GameStateChange executeCommand(String sessionId, String player, PlayerAction command) {
				return gameStateChange;
			}
		};
		
		objectUnderTest.players = mockSessions;
		
		when(mockSessions.getPlayerNameForSession(SESSION_ID)).thenReturn(mockPlayer);
		when(mockPlayer.getName()).thenReturn(PLAYER_NAME);
	}
	
	@Test
	public void shouldGetPlayerNameBeforeExecutingCommand () {
		// Given
		when (mockPlayer.getGame()).thenReturn(mockGame);
		when (mockGame.getGameId()).thenReturn(GAME_ID);
		
		// When
		GameStateChange returnedGameStateChange = objectUnderTest.executeCommand(SESSION_ID, mockPlayerAction);
		
		// Then
		assertEquals (gameStateChange, returnedGameStateChange);
		assertEquals (PLAYER_NAME, returnedGameStateChange.getPlayer());
		verify(mockSessions).getPlayerNameForSession(SESSION_ID);
	}
	
	@Test
	public void shouldGetGameFromPlayerIfOneExists () {
		// Given
		when (mockPlayer.getGame()).thenReturn(mockGame);
		when (mockGame.getGameId()).thenReturn(GAME_ID);
		
		// When
		GameStateChange returnedGameStateChange = objectUnderTest.executeCommand(SESSION_ID, mockPlayerAction);
		
		// Then
		assertEquals (gameStateChange, returnedGameStateChange);
		assertEquals (PLAYER_NAME, returnedGameStateChange.getPlayer());
		assertEquals (mockGame, returnedGameStateChange.getGame());
		verify(mockSessions).getPlayerNameForSession(SESSION_ID);
	}
	
	@Test
	public void shouldGetNoGameFromPlayerIfOneDoesntExist () {
		// Given
		when (mockPlayer.getGame()).thenReturn(null);
		
		// When
		GameStateChange returnedGameStateChange = objectUnderTest.executeCommand(SESSION_ID, mockPlayerAction);
		
		// Then
		assertEquals (gameStateChange, returnedGameStateChange);
		assertEquals (PLAYER_NAME, returnedGameStateChange.getPlayer());
		assertNull (returnedGameStateChange.getGame());
		verify(mockSessions).getPlayerNameForSession(SESSION_ID);
	}
}
