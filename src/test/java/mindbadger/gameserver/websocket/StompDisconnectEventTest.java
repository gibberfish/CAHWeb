package mindbadger.gameserver.websocket;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import mindbadger.gameserver.player.PlayerSessions;
import mindbadger.gameserver.websocket.StompDisconnectEvent;
import mindbadger.gameserver.websocket.StompHeaderAccessorWrapper;

public class StompDisconnectEventTest {
	private static final String SESSIONID = "sessionid";
	
	private StompDisconnectEvent objectUnderTest;
	
	@Mock private PlayerSessions mockSessions;
	@Mock private SessionDisconnectEvent mockSessionDisconnectEvent;
	@Mock private StompHeaderAccessorWrapper mockStompHeaderAccessorWrapper;
	@Mock private StompHeaderAccessor mockStompHeaderAccessor;
	@Mock private Message<byte[]> mockMessage;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		objectUnderTest = new StompDisconnectEvent();
		
		objectUnderTest.sessions = mockSessions;
		objectUnderTest.stompHeaderAccessorWrapper = mockStompHeaderAccessorWrapper;
	}

	@Test
	public void shouldUnregisterPlayerOnConnect () {
		// Given
		when (mockSessionDisconnectEvent.getMessage()).thenReturn (mockMessage);
		when (mockStompHeaderAccessorWrapper.wrap(mockMessage)).thenReturn(mockStompHeaderAccessor);
		when (mockStompHeaderAccessor.getSessionId()).thenReturn(SESSIONID);
		
		// When
		objectUnderTest.onApplicationEvent(mockSessionDisconnectEvent);
		
		// Then
		verify (mockSessions).removePlayerWithSession(SESSIONID);
	}
}
