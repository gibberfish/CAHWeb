package mindbadger.cah.action;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mindbadger.cah.player.Sessions;
import mindbadger.cah.websocket.pojo.GameStateChange;
import mindbadger.cah.websocket.pojo.PlayerAction;

@Component("logout")
public class LogoutAction implements Action {
	final static Logger logger = Logger.getLogger(LogoutAction.class);
	
	@Override
	public GameStateChange executeCommand(String sessionId, String player, PlayerAction command) {
        logger.info("Logout [sessionId: " + sessionId +"; name: "+ player + " ]");
        GameStateChange gsc = new GameStateChange();
        gsc.setPlayer(player);
        gsc.setCommand(command.getAction());
        gsc.setValue("Logout Succesful");
        return gsc;
    }

}