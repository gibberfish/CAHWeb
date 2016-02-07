package mindbadger.cah.websocket;

import org.apache.log4j.Logger;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GameMessageController {
	final static Logger logger = Logger.getLogger(GameMessageController.class);
	
    @MessageMapping("/gameserver")
    @SendTo("/gamestate/gameStateUpdates")
    public GameStateChange handlePlayerActionMessage(PlayerActionCommand command) throws Exception {
    	
    	GameStateChange gar = new GameStateChange();
    	gar.setPlayer("unknown");
    	gar.setCommand(command.getCommand());
    	gar.setValue(command.getValue());
    	
        return gar;
    }
}
