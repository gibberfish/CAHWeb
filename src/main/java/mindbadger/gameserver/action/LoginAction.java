package mindbadger.gameserver.action;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import mindbadger.gameserver.websocket.GameStateChange;

@Component("login")
public class LoginAction extends Action {
	final static Logger logger = Logger.getLogger(LoginAction.class);
	
	@Override
	protected GameStateChange executeCommand(String sessionId, String player, PlayerAction command) {
        logger.info("ACTION: Login [sessionId: " + sessionId +"; name: "+ player + " ]");
        GameStateChange gsc = new GameStateChange();
		return gsc;
	}

}
