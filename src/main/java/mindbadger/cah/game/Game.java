package mindbadger.cah.game;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

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
		this.players.add(player);
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
