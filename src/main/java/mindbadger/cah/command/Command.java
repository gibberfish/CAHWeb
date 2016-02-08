package mindbadger.cah.command;

import mindbadger.cah.websocket.pojo.GameStateChange;
import mindbadger.cah.websocket.pojo.PlayerActionCommand;

public interface Command {
	public GameStateChange executeCommand (String sessionId, String player, PlayerActionCommand command);
}
