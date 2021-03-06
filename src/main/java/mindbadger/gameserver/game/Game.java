package mindbadger.gameserver.game;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import mindbadger.gameserver.player.Player;

public abstract class Game {
	protected int gameId;
	protected GameType gameType;
	protected int minimumPlayers;
	protected GameState gameState;
	
	@JsonSerialize(using = PlayerListSerializer.class)
	protected List<Player> players = new ArrayList<Player> ();

	public Game (Integer gameId, GameType gameType) {
		this.gameId = gameId;
		this.gameType = gameType;
		this.gameState = GameState.NEW;
	}

	public Player addPlayerToGame (Player player) {
		if (player != null) {
			Player gameSpecificPlayer = createGameSpecificPlayer(player);
			gameSpecificPlayer.setGame(this);
			this.players.add(gameSpecificPlayer);
			return gameSpecificPlayer;
		} else {
			return player;
		}
	}
	
	public GameType getGameType() {
		return gameType;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public int getGameId() {
		return gameId;
	}

	public GameState getGameState () {
		return this.gameState;
	}
	
	public void setGameState (GameState gameState) {
		this.gameState = gameState;
	}
	
	public abstract Player createGameSpecificPlayer (Player playerNotInGame);
	public abstract int getMinimumPlayers ();
}
