package mindbadger.cah.action;

import org.springframework.beans.factory.annotation.Autowired;

import mindbadger.cah.sessions.PlayerSessions;
import mindbadger.cah.websocket.pojo.GameStateChange;
import mindbadger.cah.websocket.pojo.PlayerAction;

public abstract class Action {
	
	@Autowired
	PlayerSessions players;

	public GameStateChange executeCommand (String sessionId, PlayerAction command) {
		String playerNameForSession = players.getPlayerNameForSession(sessionId);
		GameStateChange commandResults = executeCommand(sessionId, playerNameForSession, command);
		commandResults.setPlayer(playerNameForSession);
		return commandResults;
	}
	
	protected abstract GameStateChange executeCommand (String sessionId, String player, PlayerAction command);
}
