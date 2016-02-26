package mindbadger.cah.players;

import mindbadger.cah.game.Game;

public abstract class Player {
	private String name;
	private Game game;
	
	public Player(String name) {
		this.name = name;
	}
	
	public Player (Player player) {
		this (player.getName());
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
