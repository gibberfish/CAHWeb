package mindbadger.gameserver.player;

import mindbadger.gameserver.game.Game;

public interface Player {
	public String getName();
	public Game getGame();
	public void setGame(Game game);
	public Player getRootPlayer();
	public PlayerState getPlayerState();
	public void setPlayerState (PlayerState state);
}
