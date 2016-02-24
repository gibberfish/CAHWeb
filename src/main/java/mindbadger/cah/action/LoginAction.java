package mindbadger.cah.action;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import mindbadger.cah.websocket.GameStateChange;

@Component("login")
public class LoginAction extends Action {
	final static Logger logger = Logger.getLogger(LoginAction.class);
	
	@Override
	protected GameStateChange executeCommand(String sessionId, String player, PlayerAction command) {
        logger.info("ACTION: Login [sessionId: " + sessionId +"; name: "+ player + " ]");
        GameStateChange gsc = new GameStateChange();
        gsc.setPlayer(player);
        gsc.setCommand(command.getAction());
        gsc.setValue("Login Succesful");
		return gsc;
	}

}
