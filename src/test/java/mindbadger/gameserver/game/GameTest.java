package mindbadger.gameserver.game;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import mindbadger.gameserver.game.Game;
import mindbadger.gameserver.game.GameType;
import mindbadger.gameserver.player.Player;
import mindbadger.gameserver.player.PlayerNotInGame;

public class GameTest {
	private Game objectUnderTest;

	private static final Integer GAME_ID = 123;
	private static final String PLAYER1_NAME = "Mark";
	private static final String PLAYER2_NAME = "Anne";
	private static final String TYPE_1_GAME_URL = "Type 1 url";
	private static final String TYPE_1_DISPLAY_NAME = "Game Type 1";
	private static final String TYPE1_ID = "TYPE1";
	private static final GameType GAME_TYPE_1 = new GameType(TYPE1_ID,TYPE_1_DISPLAY_NAME,TYPE_1_GAME_URL);
	private static final Player PLAYER1 = new PlayerNotInGame(PLAYER1_NAME);
	private static final Player PLAYER2 = new PlayerNotInGame(PLAYER2_NAME);
	
	@Before
	public void init () {
		objectUnderTest = new Game (GAME_ID, GAME_TYPE_1) {
			@Override
			public Player createGameSpecificPlayer(Player playerNotInGame) {
				return PLAYER2;
			}
		};
	}
	
	@Test
	public void shouldCreateNewGame () {
		// Given
		
		// When
		Integer gameId = objectUnderTest.getGameId();
		GameType gameType = objectUnderTest.getGameType();
		List<Player> players = objectUnderTest.getPlayers();
		
		// Then
		assertEquals (GAME_ID, gameId);
		assertEquals (GAME_TYPE_1, gameType);
		assertEquals (0, players.size());
	}

	@Test
	public void shouldAddPlayerToGame () {
		// Given
		
		// When
		objectUnderTest.addPlayerToGame(PLAYER1);
		
		
		// Then
		List<Player> players = objectUnderTest.getPlayers();
		assertEquals (1, players.size());
		
		Player player = players.get(0);
		assertEquals (PLAYER2, player);
		assertEquals (objectUnderTest, PLAYER2.getGame());
	}
}
