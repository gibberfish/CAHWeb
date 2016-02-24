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
import mindbadger.cah.action.PlayerAction;
import mindbadger.cah.players.PlayerSessions;

public class GameMessageControllerTest {
	
	private static final String PLAYER_ACTION = "login";
	private static final String INVALID_PLAYER_ACTION = "blahblah";
	private static final String SESSION_ID1 = "sessionId1";

	private GameMessageController objectUnderTest;
	
	@Mock
	private SimpMessageHeaderAccessor mockSimpMessageHeaderAccessor;
	
	@Mock
	private PlayerSessions mockSessions;
	
	@Mock
	private PlayerAction mockPlayerAction;
	
	@Mock
	private Action mockAction;

	@Mock
	private Action mockInvalidAction;

	@Mock
	private GameStateChange mockGameStateChange;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		objectUnderTest = new GameMessageController(); 
		
		Map<String, Action> actions = new HashMap<String, Action> ();
		actions.put(PLAYER_ACTION, mockAction);
		objectUnderTest.actions = actions;
		objectUnderTest.invalidAction = mockInvalidAction;
	}
	
	@Test
	public void shouldReturnAnInvalidGameStateChangeIfNoCommandFound () throws Exception {
		// Given
		when(mockSimpMessageHeaderAccessor.getSessionId()).thenReturn(SESSION_ID1);
		when(mockPlayerAction.getAction()).thenReturn(INVALID_PLAYER_ACTION);
		when(mockAction.executeCommand(SESSION_ID1, mockPlayerAction)).thenReturn(mockGameStateChange);
		
		// When
		GameStateChange gsc = objectUnderTest.handlePlayerActionMessage(mockSimpMessageHeaderAccessor, mockPlayerAction);
		
		// Then
		verify(mockAction,never()).executeCommand(SESSION_ID1, mockPlayerAction);
		verify(mockInvalidAction,times(1)).executeCommand(SESSION_ID1, mockPlayerAction);
	}
	
	@Test
	public void shouldExecuteACommandIfFound () throws Exception {
		// Given
		when(mockSimpMessageHeaderAccessor.getSessionId()).thenReturn(SESSION_ID1);
		when(mockPlayerAction.getAction()).thenReturn(PLAYER_ACTION);
		when(mockAction.executeCommand(SESSION_ID1, mockPlayerAction)).thenReturn(mockGameStateChange);
		
		// When
		GameStateChange gsc = objectUnderTest.handlePlayerActionMessage(mockSimpMessageHeaderAccessor, mockPlayerAction);
		
		// Then
		verify(mockAction).executeCommand(SESSION_ID1, mockPlayerAction);
		assertEquals (gsc, mockGameStateChange);
	}

}
