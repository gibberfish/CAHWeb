package mindbadger.gameserver.player;

import com.fasterxml.jackson.annotation.JsonIgnore;

import mindbadger.gameserver.game.Game;

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

	@JsonIgnore
	@Override
	public Player getRootPlayer() {
		return this;
	}

	@Override
	public PlayerState getPlayerState() {
		return PlayerState.NOT_IN_GAME;
	}

	@Override
	public void setPlayerState(PlayerState state) {
		throw new IllegalStateException("Cannot change the state");
	}
}
