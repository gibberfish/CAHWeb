package mindbadger.gameserver.player;

import mindbadger.gameserver.game.Game;

public abstract class PlayerInGame implements Player {

	private Player playerNotInGame;
	
	public PlayerInGame(Player playerNotInGame) {
		this.playerNotInGame = playerNotInGame;
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
}
