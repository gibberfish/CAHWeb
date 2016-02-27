package mindbadger.gameserver.game;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import mindbadger.gameserver.player.Player;

public abstract class Game {
	protected int gameId;
	protected GameType gameType;
	
	@JsonSerialize(using = PlayerListSerializer.class)
	protected List<Player> players = new ArrayList<Player> ();

	public Game (Integer gameId, GameType gameType) {
		this.gameId = gameId;
		this.gameType = gameType;
	}

	public void addPlayerToGame (Player player) {
		if (player != null) {
			Player gameSpecificPlayer = createGameSpecificPlayer(player);
			gameSpecificPlayer.setGame(this);
			this.players.add(gameSpecificPlayer);
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
	
	public abstract Player createGameSpecificPlayer (Player playerNotInGame);
}
