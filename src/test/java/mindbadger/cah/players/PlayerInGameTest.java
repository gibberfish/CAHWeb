package mindbadger.cah.players;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import mindbadger.cah.game.Game;

public class PlayerInGameTest {
	private PlayerInGame objectUnderTest;
	
	private static final String PLAYER_NAME = "Mark";
	
	@Mock
	private Game mockGame;

	private Player playerNotInGame = new PlayerNotInGame (PLAYER_NAME);

	@Before
	public void init () {
		MockitoAnnotations.initMocks(this);
		
		playerNotInGame.setGame(mockGame);
		
		objectUnderTest = new PlayerInGame (playerNotInGame) {
		};
	}
	
	@Test
	public void shouldCreatePlayerInGame () {
		// Given
		
		// When
		objectUnderTest = new PlayerInGame (playerNotInGame) {
		};
		
		// Then
		assertEquals (mockGame, objectUnderTest.getGame());
		assertEquals (PLAYER_NAME, objectUnderTest.getName());
		assertEquals (playerNotInGame, objectUnderTest.getRootPlayer());
	}
	
	@Test
	public void shouldBeAbleToChangeGameForPlayerInGame () {
		// Given
		objectUnderTest = new PlayerInGame (playerNotInGame) {
		};
		
		// When
		objectUnderTest.setGame(null);
		
		// Then
		assertNull (objectUnderTest.getGame());
		assertEquals (PLAYER_NAME, objectUnderTest.getName());
		assertEquals (playerNotInGame, objectUnderTest.getRootPlayer());
	}
}
