package mindbadger.cah.action;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import mindbadger.cah.websocket.GameStateChange;

@Component("logout")
public class LogoutAction extends Action {
	final static Logger logger = Logger.getLogger(LogoutAction.class);
	
	@Override
	protected GameStateChange executeCommand(String sessionId, String player, PlayerAction command) {
        logger.info("Logout [sessionId: " + sessionId +"; name: "+ player + " ]");
        GameStateChange gsc = new GameStateChange();
        gsc.setPlayer(player);
        gsc.setCommand(command.getAction());
        gsc.setValue("Logout Succesful");
        return gsc;
    }

}
