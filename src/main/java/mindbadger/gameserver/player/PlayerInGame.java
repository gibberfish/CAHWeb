package mindbadger.gameserver.player;

import com.fasterxml.jackson.annotation.JsonIgnore;

import mindbadger.gameserver.game.Game;

public abstract class PlayerInGame implements Player {

	@JsonIgnore
	private Player playerNotInGame;
	private PlayerState playerState;
	
	public PlayerInGame(Player playerNotInGame) {
		this.playerNotInGame = playerNotInGame;
		this.playerState = PlayerState.JOINED;
	}

	@Override
	public String getName() {
		return playerNotInGame.getName();
	}

	@Override
	public Game getGame() {
		return playerNotInGame.getGame();
	}

	@Override
	public void setGame(Game game) {
		this.playerNotInGame.setGame(game);
	}

	@Override
	public Player getRootPlayer() {
		return playerNotInGame;
	}
	
	@Override
	public PlayerState getPlayerState() {
		return this.playerState;
	}

	@Override
	public void setPlayerState(PlayerState state) {
		this.playerState = state;
	}
}
