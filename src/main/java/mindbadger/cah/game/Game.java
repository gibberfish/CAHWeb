package mindbadger.cah.game;

import java.util.ArrayList;
import java.util.List;

public class Game {
	private int gameId;
	private GameType gameType;
	private List<String> players = new ArrayList<String> ();

	public Game (int gameId, GameType gameType) {
		this.gameId = gameId;
		this.gameType = gameType;
	}

	public void addPlayerToGame (String player) {
		this.players.add(player);
	}
	
	public GameType getGameType() {
		return gameType;
	}

	public List<String> getPlayers() {
		return players;
	}

	public int getGameId() {
		return gameId;
	}
}
