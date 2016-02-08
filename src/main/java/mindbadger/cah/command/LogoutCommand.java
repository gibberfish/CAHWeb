package mindbadger.cah.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import mindbadger.cah.player.Players;
import mindbadger.cah.websocket.pojo.GameStateChange;
import mindbadger.cah.websocket.pojo.PlayerActionCommand;

@Component("logout")
public class LogoutCommand implements Command {

	@Autowired
	private Players players;
	
	@Override
	public GameStateChange executeCommand(String sessionId, String player, PlayerActionCommand command) {
		// TODO Auto-generated method stub
		return null;
	}

}
