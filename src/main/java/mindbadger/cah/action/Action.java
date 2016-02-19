package mindbadger.cah.action;

import org.springframework.beans.factory.annotation.Autowired;

import mindbadger.cah.game.Game;
import mindbadger.cah.game.Player;
import mindbadger.cah.sessions.PlayerSessions;
import mindbadger.cah.websocket.pojo.GameStateChange;
import mindbadger.cah.websocket.pojo.PlayerAction;

public abstract class Action {
	
	@Autowired
	PlayerSessions players;

	public GameStateChange executeCommand (String sessionId, PlayerAction command) {
		Player player = players.getPlayerNameForSession(sessionId);
		String playerNameForSession = player.getName();
		GameStateChange commandResults = executeCommand(sessionId, playerNameForSession, command);
		commandResults.setPlayer(playerNameForSession);
		Game game = player.getGame();
		if (game != null) {
			commandResults.setGameId(game.getGameId());
		}
		return commandResults;
	}
	
	protected abstract GameStateChange executeCommand (String sessionId, String player, PlayerAction command);
}
