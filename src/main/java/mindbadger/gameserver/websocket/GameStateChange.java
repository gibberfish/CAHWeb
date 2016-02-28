package mindbadger.gameserver.websocket;

import mindbadger.gameserver.game.Game;

public class GameStateChange {
    
	private String player;
	private String command;
	private Game game;
	
	public String getPlayer() {
		return player;
	}
	public void setPlayer(String player) {
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
