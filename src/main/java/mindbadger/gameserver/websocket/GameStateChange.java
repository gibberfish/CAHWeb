package mindbadger.gameserver.websocket;

import mindbadger.gameserver.game.Game;
import mindbadger.gameserver.player.Player;

public class GameStateChange {
    
	private Player player;
	private String command;
	private Game game;
	
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
}
