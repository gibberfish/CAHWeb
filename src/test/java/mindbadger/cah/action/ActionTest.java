package mindbadger.cah.action;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import mindbadger.cah.sessions.PlayerSessions;
import mindbadger.cah.websocket.pojo.GameStateChange;
import mindbadger.cah.websocket.pojo.PlayerAction;

public class ActionTest {
	private static final String PLAYER_NAME = "PLAYER1";
	private static final String SESSION_ID = "Session1";

	private Action objectUnderTest;
	
	private GameStateChange gameStateChange = new GameStateChange();
	
	@Mock
	private PlayerAction mockPlayerAction;
	
	@Mock
	private PlayerSessions mockSessions;

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
		
		when(mockSessions.getPlayerNameForSession(SESSION_ID)).thenReturn(PLAYER_NAME);
	}
	
	@Test
	public void shouldGetPlayerNameBeforeExecutingCommand () {
		// Given
		
		// When
		GameStateChange returnedGameStateChange = objectUnderTest.executeCommand(SESSION_ID, mockPlayerAction);
		
		// Then
		assertEquals (gameStateChange, returnedGameStateChange);
		assertEquals (PLAYER_NAME, returnedGameStateChange.getPlayer());
		verify(mockSessions).getPlayerNameForSession(SESSION_ID);
	}
}
