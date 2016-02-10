package mindbadger.cah.websocket;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionConnectEvent;

import mindbadger.cah.player.Sessions;

public class StompConnectEventTest {
	private static final String NAME = "Mark";
	private static final String SESSIONID = "sessionid";

	private StompConnectEvent objectUnderTest;
	
	@Mock private Sessions mockSessions;
	@Mock private SessionConnectEvent mockSessionConnectEvent;
	@Mock private StompHeaderAccessorWrapper mockStompHeaderAccessorWrapper;
	@Mock private StompHeaderAccessor mockStompHeaderAccessor;
	@Mock private Message<byte[]> mockMessage;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		objectUnderTest = new StompConnectEvent();
		
		objectUnderTest.sessions = mockSessions;
		objectUnderTest.stompHeaderAccessorWrapper = mockStompHeaderAccessorWrapper;
	}
	
	@Test
	public void shouldRegisterPlayerOnConnect () {
		// Given
		List<String> nativeHeaders = new ArrayList<String> ();
		nativeHeaders.add(NAME);
		when (mockSessionConnectEvent.getMessage()).thenReturn (mockMessage);
		when (mockStompHeaderAccessorWrapper.wrap(mockMessage)).thenReturn(mockStompHeaderAccessor);
		when (mockStompHeaderAccessor.getNativeHeader("name")).thenReturn(nativeHeaders);
		when (mockStompHeaderAccessor.getSessionId()).thenReturn(SESSIONID);
		
		// When
		objectUnderTest.onApplicationEvent(mockSessionConnectEvent);
		
		// Then
		verify (mockSessions).addPlayerSession(NAME, SESSIONID);
	}
}
