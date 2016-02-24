package mindbadger.cah.action;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import mindbadger.cah.websocket.GameStateChange;

@Component("invalidAction")
public class InvalidAction extends Action {
	final static Logger logger = Logger.getLogger(InvalidAction.class);
	
	@Override
	protected GameStateChange executeCommand(String sessionId, String player, PlayerAction command) {
        logger.info("ACTION: Login [sessionId: " + sessionId +"; name: "+ player + " ]");
        GameStateChange gsc = new GameStateChange();
        gsc.setPlayer(player);
        gsc.setCommand(command.getAction());
        gsc.setValue("INVALID ACTION RECEIVED");
		return gsc;
	}

}
