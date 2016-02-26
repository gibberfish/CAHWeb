package mindbadger.cah.players;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import mindbadger.cah.game.Game;

public class PlayerSessionsTest {
	private static final String SESSION1 = "SESSION1";
	private static final String SESSION2 = "SESSION2";
	private static final String PLAYER1 = "PLAYER1";
	private PlayerSessions objectUnderTest;
	
	@Mock
	Player mockPlayerNotInGame;
	
	@Mock
	Game mockGame;
	
	@Before
	public void init () {
		MockitoAnnotations.initMocks(this);
		objectUnderTest = new PlayerSessions ();
		
		when(mockPlayerNotInGame.getGame()).thenReturn(mockGame);
		when(mockPlayerNotInGame.getName()).thenReturn(PLAYER1);
		when(mockPlayerNotInGame.getRootPlayer()).thenReturn(mockPlayerNotInGame);
	}
	
	@Test
	public void shouldNotReturnANonExistentSession () {
		// Given
		
		// When
		Player player1 = objectUnderTest.getPlayerForSession(SESSION1);
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
		Player player1 = objectUnderTest.getPlayerForSession(SESSION1);
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
		Player playerForSession1 = objectUnderTest.getPlayerForSession(SESSION1);
		Player playerForSession2 = objectUnderTest.getPlayerForSession(SESSION2);
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
		Player player1InSession = objectUnderTest.getPlayerForSession(SESSION1);
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
		Player player1InSession = objectUnderTest.getPlayerForSession(SESSION1);
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
		Player playerInGame = new CardsAgainstHumanityPlayer(mockPlayerNotInGame);
		
		// When
		objectUnderTest.replacePlayerNotInGameWithGameSpecificPlayer(playerInGame);
		
		// Then
		Player player = objectUnderTest.getPlayer(PLAYER1);
		assertEquals (playerInGame, player);
		
		player = objectUnderTest.getPlayerForSession(SESSION1);
		assertEquals (playerInGame, player);
	}

	@Test
	public void shouldReRegisterPlayerWhenTheyLeaveAGame () {
		// Given
		objectUnderTest.addPlayerSession(PLAYER1, SESSION1);
		Player playerInGame = new CardsAgainstHumanityPlayer(mockPlayerNotInGame);
		objectUnderTest.replacePlayerNotInGameWithGameSpecificPlayer(playerInGame);
		
		// When
		objectUnderTest.revertToRootPlayer (PLAYER1);
		
		// Then
		Player player = objectUnderTest.getPlayer(PLAYER1);
		assertEquals (mockPlayerNotInGame, player);
		
		player = objectUnderTest.getPlayerForSession(SESSION1);
		assertEquals (mockPlayerNotInGame, player);
	}

}
