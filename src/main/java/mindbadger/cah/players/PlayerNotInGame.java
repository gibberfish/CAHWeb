package mindbadger.cah.players;

import mindbadger.cah.game.Game;

public class PlayerNotInGame implements Player {

	private String name;
	private Game game;
	
	public PlayerNotInGame(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public Game getGame() {
		return game;
	}
	
	@Override
	public void setGame(Game game) {
		this.game = game;
	}

	@Override
	public Player getRootPlayer() {
		return this;
	}
}
