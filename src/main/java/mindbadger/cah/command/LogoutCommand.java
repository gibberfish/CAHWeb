package mindbadger.cah.command;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mindbadger.cah.player.Players;
import mindbadger.cah.websocket.pojo.GameStateChange;
import mindbadger.cah.websocket.pojo.PlayerActionCommand;

@Component("logout")
public class LogoutCommand implements Command {
	final static Logger logger = Logger.getLogger(LogoutCommand.class);
	
	@Override
	public GameStateChange executeCommand(String sessionId, String player, PlayerActionCommand command) {
        logger.info("Logout [sessionId: " + sessionId +"; name: "+ player + " ]");
        GameStateChange gsc = new GameStateChange();
        gsc.setPlayer(player);
        gsc.setCommand(command.getCommand());
        gsc.setValue("Logout Succesful");
        return gsc;
    }

}
