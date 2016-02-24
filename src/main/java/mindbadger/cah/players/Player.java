package mindbadger.cah.players;

import mindbadger.cah.game.Game;

public class Player {
	private String name;
	private Game game;
	
	public Player(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
}
