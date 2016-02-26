package mindbadger.cah.players;

import mindbadger.cah.game.Game;

public interface Player {
	public String getName();
	public Game getGame();
	public void setGame(Game game);
	public Player getRootPlayer();
}
