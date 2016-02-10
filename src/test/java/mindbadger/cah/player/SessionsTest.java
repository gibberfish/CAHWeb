package mindbadger.cah.player;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SessionsTest {
	private static final String SESSION1 = "SESSION1";
	private static final String SESSION2 = "SESSION2";
	private static final String PLAYER1 = "PLAYER1";
	private Sessions objectUnderTest;
	
	@Before
	public void init () {
		objectUnderTest = new Sessions ();
	}
	
	@Test
	public void shouldNotReturnANonExistentSession () {
		// Given
		
		// When
		String session1 = objectUnderTest.getPlayerNameForSession(SESSION1);
		String player1 = objectUnderTest.getSessionForPlayer(PLAYER1);
		
		// Then
		assertEquals (null, session1);
		assertEquals (null, player1);
	}
	
	@Test
	public void shouldReturnASession () {
		// Given
		objectUnderTest.addPlayerSession(PLAYER1, SESSION1);
		
		// When
		String player1 = objectUnderTest.getPlayerNameForSession(SESSION1);
		String session1 = objectUnderTest.getSessionForPlayer(PLAYER1);
		
		// Then
		assertEquals (SESSION1, session1);
		assertEquals (PLAYER1, player1);
	}

	@Test
	public void shouldAllowAPlayerToChangeSession () {
		// Given
		objectUnderTest.addPlayerSession(PLAYER1, SESSION1);
		objectUnderTest.addPlayerSession(PLAYER1, SESSION2);
		
		// When
		String playerForSession1 = objectUnderTest.getPlayerNameForSession(SESSION1);
		String playerForSession2 = objectUnderTest.getPlayerNameForSession(SESSION2);
		String session = objectUnderTest.getSessionForPlayer(PLAYER1);
		
		// Then
		assertEquals (SESSION2, session);
		assertEquals (null, playerForSession1);
		assertEquals (PLAYER1, playerForSession2);
	}
	
	@Test
	public void shouldAllowASessionToBeRemoved () {
		// Given
		objectUnderTest.addPlayerSession(PLAYER1, SESSION1);
		
		// When
		objectUnderTest.removePlayerWithSession(SESSION1);
		
		// Then
		String player1 = objectUnderTest.getPlayerNameForSession(SESSION1);
		String session1 = objectUnderTest.getSessionForPlayer(PLAYER1);
		assertEquals (null, session1);
		assertEquals (null, player1);
	}
}
