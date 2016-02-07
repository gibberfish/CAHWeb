package mindbadger.cah.websocket;

import org.apache.log4j.Logger;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class GameMessageController {
	final static Logger logger = Logger.getLogger(GameMessageController.class);
	
    @MessageMapping("/gameserver")
    @SendTo("/gamestate/gameStateUpdates")
    public GameStateChange handlePlayerActionMessage(SimpMessageHeaderAccessor headerAccessor, @Payload PlayerActionCommand command) throws Exception {
    	
    	GameStateChange gar = new GameStateChange();
    	gar.setPlayer(headerAccessor.getSessionId());
    	gar.setCommand(command.getCommand());
    	gar.setValue(command.getValue());
    	
    	//logger.info("Created new response in handlePlayerActionMessage in object " + this);
    	
        return gar;
    }
}
