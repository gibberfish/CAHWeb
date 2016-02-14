package mindbadger.cah.game;

import java.util.ArrayList;
import java.util.List;

public class Game {
	private GameType gameType;
	private List<String> players = new ArrayList<String> ();

	public Game (GameType gameType) {
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
}
