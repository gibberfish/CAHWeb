package mindbadger.cah.action;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import mindbadger.cah.game.Game;
import mindbadger.cah.players.Player;
import mindbadger.cah.players.PlayerSessions;
import mindbadger.cah.websocket.GameStateChange;

public abstract class Action {
	final static Logger logger = Logger.getLogger(Action.class);
	
	@Autowired
	PlayerSessions players;

	public GameStateChange executeCommand (String sessionId, PlayerAction command) {
		Player player = players.getPlayerNameForSession(sessionId);
		String playerNameForSession = player.getName();
		GameStateChange commandResults = executeCommand(sessionId, playerNameForSession, command);
		
		commandResults.setPlayer(playerNameForSession);
		Game game = player.getGame();
		logger.info("Action has set player name " + playerNameForSession + " onto the game state change.");
		logger.info("  this player has game " + game);
		if (game != null) {
			commandResults.setGame(game);
		}
		return commandResults;
	}
	
	protected abstract GameStateChange executeCommand (String sessionId, String player, PlayerAction command);
}
