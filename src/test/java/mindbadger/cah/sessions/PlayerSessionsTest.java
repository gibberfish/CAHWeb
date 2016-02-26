package mindbadger.cah.sessions;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import mindbadger.cah.game.CardsAgainstHumanityGame;
import mindbadger.cah.players.CardsAgainstHumanityPlayer;
import mindbadger.cah.players.Player;
import mindbadger.cah.players.PlayerNotInGame;
import mindbadger.cah.players.PlayerSessions;

public class PlayerSessionsTest {
	private static final String SESSION1 = "SESSION1";
	private static final String SESSION2 = "SESSION2";
	private static final String PLAYER1 = "PLAYER1";
	private PlayerSessions objectUnderTest;
	
	@Before
	public void init () {
		objectUnderTest = new PlayerSessions ();
	}
	
	@Test
	public void shouldNotReturnANonExistentSession () {
		// Given
		
		// When
		Player player1 = objectUnderTest.getPlayerNameForSession(SESSION1);
		String session1 = objectUnderTest.getSessionForPlayer(PLAYER1);
		
		// Then
		assertEquals (null, session1);
		assertEquals (null, player1);
	}
	
	@Test
	public void shouldReturnASession () {
		// Given
		objectUnderTest.addPlayerSession(PLAYER1, SESSION1);
		
		// When
		Player player1 = objectUnderTest.getPlayerNameForSession(SESSION1);
		String session1 = objectUnderTest.getSessionForPlayer(PLAYER1);
		
		// Then
		assertEquals (SESSION1, session1);
		assertEquals (PLAYER1, player1.getName());
		assertTrue (player1 instanceof PlayerNotInGame);
	}

	@Test
	public void shouldAllowAPlayerToChangeSession () {
		// Given
		objectUnderTest.addPlayerSession(PLAYER1, SESSION1);
		objectUnderTest.addPlayerSession(PLAYER1, SESSION2);
		
		// When
		Player playerForSession1 = objectUnderTest.getPlayerNameForSession(SESSION1);
		Player playerForSession2 = objectUnderTest.getPlayerNameForSession(SESSION2);
		String session = objectUnderTest.getSessionForPlayer(PLAYER1);
		
		// Then
		assertEquals (SESSION2, session);
		assertEquals (null, playerForSession1);
		assertEquals (PLAYER1, playerForSession2.getName());
	}
	
	@Test
	public void shouldAllowASessionToBeRemoved () {
		// Given
		objectUnderTest.addPlayerSession(PLAYER1, SESSION1);
		
		// When
		objectUnderTest.removePlayerWithSession(SESSION1);
		
		// Then
		Player player1InSession = objectUnderTest.getPlayerNameForSession(SESSION1);
		String session1 = objectUnderTest.getSessionForPlayer(PLAYER1);
		Player player1 = objectUnderTest.getPlayer(PLAYER1);
		assertNull (session1);
		assertNull (player1InSession);
		assertNotNull (player1);
	}
	
	@Test
	public void shouldNotFailIfRemovingSessionWhereThePlayerNoLongerExists () {
		// Given
		objectUnderTest.addPlayerSession(PLAYER1, SESSION1);
		objectUnderTest.removePlayerWithSession(SESSION1);
		
		// When
		objectUnderTest.removePlayerWithSession(SESSION1);
		
		// Then
		Player player1InSession = objectUnderTest.getPlayerNameForSession(SESSION1);
		String session1 = objectUnderTest.getSessionForPlayer(PLAYER1);
		Player player1 = objectUnderTest.getPlayer(PLAYER1);
		assertNull (session1);
		assertNull (player1InSession);
		assertNotNull (player1);
	}

	@Test
	public void shouldReRegisterPlayerWhenTheyJoinAGame () {
		// Given
		objectUnderTest.addPlayerSession(PLAYER1, SESSION1);
		Player playerInGame = new CardsAgainstHumanityPlayer(PLAYER1);
		
		// When
		objectUnderTest.registerPlayerInGame(playerInGame);
		
		// Then
		
	}
	
}
