package mindbadger.gameserver.player;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import mindbadger.gameserver.game.Game;
import mindbadger.gameserver.player.Player;
import mindbadger.gameserver.player.PlayerInGame;
import mindbadger.gameserver.player.PlayerNotInGame;

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
		assertEquals (PlayerState.JOINED, objectUnderTest.getPlayerState());
	}
	
	@Test
	public void shouldChangePlayerState () {
		// Given
		objectUnderTest = new PlayerInGame (playerNotInGame) {
		};
		
		// When
		objectUnderTest.setPlayerState(PlayerState.PLAYING);
			
		// Then
		assertEquals (PlayerState.PLAYING, objectUnderTest.getPlayerState());
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
