package mindbadger.cah.action;

import mindbadger.cah.websocket.pojo.GameStateChange;
import mindbadger.cah.websocket.pojo.PlayerAction;

public interface Action {
	public GameStateChange executeCommand (String sessionId, String player, PlayerAction command);
}
