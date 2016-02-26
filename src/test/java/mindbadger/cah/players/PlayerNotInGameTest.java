package mindbadger.cah.players;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import mindbadger.cah.game.Game;

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
