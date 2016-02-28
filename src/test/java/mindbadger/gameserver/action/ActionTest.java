package mindbadger.gameserver.action;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import mindbadger.gameserver.action.Action;
import mindbadger.gameserver.action.PlayerAction;
import mindbadger.gameserver.game.Game;
import mindbadger.gameserver.player.Player;
import mindbadger.gameserver.player.PlayerSessions;
import mindbadger.gameserver.websocket.GameStateChange;

public class ActionTest {
	private static final String COMMAND = "COMMAND";
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
		
		when(mockSessions.getPlayerForSession(SESSION_ID)).thenReturn(mockPlayer);
		when(mockPlayer.getName()).thenReturn(PLAYER_NAME);
		when(mockPlayerAction.getAction()).thenReturn(COMMAND);
	}
	
	@Test
	public void shouldGetPlayerNameAndCommandBeforeExecutingCommand () {
		// Given
		when (mockPlayer.getGame()).thenReturn(mockGame);
		when (mockGame.getGameId()).thenReturn(GAME_ID);
		
		// When
		GameStateChange returnedGameStateChange = objectUnderTest.executeCommand(SESSION_ID, mockPlayerAction);
		
		// Then
		assertEquals (gameStateChange, returnedGameStateChange);
		assertEquals (mockPlayer, returnedGameStateChange.getPlayer());
		assertEquals (COMMAND, returnedGameStateChange.getCommand());
		verify(mockSessions).getPlayerForSession(SESSION_ID);
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
		assertEquals (mockPlayer, returnedGameStateChange.getPlayer());
		assertEquals (mockGame, returnedGameStateChange.getGame());
		verify(mockSessions).getPlayerForSession(SESSION_ID);
	}
	
	@Test
	public void shouldGetNoGameFromPlayerIfOneDoesntExist () {
		// Given
		when (mockPlayer.getGame()).thenReturn(null);
		
		// When
		GameStateChange returnedGameStateChange = objectUnderTest.executeCommand(SESSION_ID, mockPlayerAction);
		
		// Then
		assertEquals (gameStateChange, returnedGameStateChange);
		assertEquals (mockPlayer, returnedGameStateChange.getPlayer());
		assertNull (returnedGameStateChange.getGame());
		verify(mockSessions).getPlayerForSession(SESSION_ID);
	}
}
