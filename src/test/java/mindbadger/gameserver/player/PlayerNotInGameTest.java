package mindbadger.gameserver.player;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import mindbadger.gameserver.game.Game;
import mindbadger.gameserver.player.Player;
import mindbadger.gameserver.player.PlayerNotInGame;

public class PlayerNotInGameTest {
	private static final String PLAYER_NAME = "Mark";
	
	@Mock
	private Game mockGame;

	@Before
	public void init () {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldCreatePlayerNotInGame () {
		// Given
		
		// When
		Player player = new PlayerNotInGame(PLAYER_NAME);
		
		// Then
		assertNull (player.getGame());
		assertEquals (PLAYER_NAME, player.getName());
		assertEquals (player, player.getRootPlayer());
		assertEquals (PlayerState.NOT_IN_GAME, player.getPlayerState());
	}

	@Test
	public void shouldNotBeAbleToChangeState () {
		// Given
		Player player = new PlayerNotInGame(PLAYER_NAME);
		
		// When
		try {
			player.setPlayerState(PlayerState.JOINED);
			fail("Should not be able to change state");
		} catch (Exception e) {
			assertEquals ("Cannot change the state", e.getMessage());
		}
	}
	
	@Test
	public void shouldAddPlayerToGame () {
		// Given
		Player player = new PlayerNotInGame(PLAYER_NAME);
		
		// When
		player.setGame(mockGame);
		
		// Then
		assertEquals (mockGame, player.getGame());
	}
}
