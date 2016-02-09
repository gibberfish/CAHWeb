package mindbadger.cah.command;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import mindbadger.cah.websocket.pojo.GameStateChange;
import mindbadger.cah.websocket.pojo.PlayerActionCommand;

@Component("login")
public class LoginCommand implements Command {
	final static Logger logger = Logger.getLogger(LoginCommand.class);
	
	@Override
	public GameStateChange executeCommand(String sessionId, String player, PlayerActionCommand command) {
        logger.info("Login [sessionId: " + sessionId +"; name: "+ player + " ]");
        GameStateChange gsc = new GameStateChange();
        gsc.setPlayer(player);
        gsc.setCommand(command.getCommand());
        gsc.setValue("Login Succesful");
		return gsc;
	}

}
