package mindbadger.cah.game;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import mindbadger.cah.players.Player;

public class Game {
	private int gameId;
	private GameType gameType;
	
	@JsonSerialize(using = GameSerializer.class)
	private List<Player> players = new ArrayList<Player> ();

	public Game (int gameId, GameType gameType) {
		this.gameId = gameId;
		this.gameType = gameType;
	}

	public void addPlayerToGame (Player player) {
		if (player != null) {
			this.players.add(player);
			player.setGame(this);
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
}
