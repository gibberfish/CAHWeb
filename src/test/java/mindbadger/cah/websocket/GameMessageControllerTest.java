package mindbadger.cah.websocket;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

import mindbadger.cah.action.Action;
import mindbadger.cah.player.Sessions;
import mindbadger.cah.websocket.pojo.GameStateChange;
import mindbadger.cah.websocket.pojo.PlayerAction;

public class GameMessageControllerTest {
	
	private static final String PLAYER_ACTION = "login";
	private static final String PLAYER1 = "player1";
	private static final String SESSION_ID1 = "sessionId1";
	private static final String ACTION1 = "action1";

	private GameMessageController objectUnderTest;
	
	@Mock
	private SimpMessageHeaderAccessor mockSimpMessageHeaderAccessor;
	
	@Mock
	private Sessions mockSessions;
	
	@Mock
	private PlayerAction mockPlayerAction;
	
	@Mock
	private Action mockAction;
	
	@Mock
	private GameStateChange mockGameStateChange;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		objectUnderTest = new GameMessageController(); 
		
		Map<String, Action> actions = new HashMap<String, Action> ();
		actions.put(ACTION1, mockAction);
		objectUnderTest.actions = actions;
		
		objectUnderTest.players = mockSessions;
	}
	
	@Test
	public void test () throws Exception {
		// Given
		when(mockSimpMessageHeaderAccessor.getSessionId()).thenReturn(SESSION_ID1);
		when(mockSessions.getPlayerNameForSession(SESSION_ID1)).thenReturn(PLAYER1);
		when(mockPlayerAction.getAction()).thenReturn(PLAYER_ACTION);
		when(mockAction.executeCommand(SESSION_ID1, PLAYER1, mockPlayerAction)).thenReturn(mockGameStateChange);
		
		// When
		GameStateChange gsc = objectUnderTest.handlePlayerActionMessage(mockSimpMessageHeaderAccessor, mockPlayerAction);
		
		// Then
		verify(mockAction.executeCommand(SESSION_ID1, PLAYER1, mockPlayerAction));
		assertEquals (gsc, eq(mockGameStateChange));
	}
}
