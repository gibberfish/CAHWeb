package mindbadger.gameserver.websocket;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import mindbadger.gameserver.action.Action;
import mindbadger.gameserver.action.PlayerAction;
import mindbadger.gameserver.player.PlayerSessions;

@Controller
public class GameMessageController {
	final static Logger logger = Logger.getLogger(GameMessageController.class);
	
	@Autowired
	Map<String, Action> actions;
	
	@Autowired
	@Qualifier("invalidAction")
	Action invalidAction;
	
    @MessageMapping("/gameserver")
    @SendTo("/gamestate/gameStateUpdates")
    public GameStateChange handlePlayerActionMessage(SimpMessageHeaderAccessor headerAccessor, @Payload PlayerAction action) throws Exception {
    	logger.info("Received Player Action Message: " + action.getAction());

    	String sessionId = headerAccessor.getSessionId();
    	Action commandObject = actions.get(action.getAction());
    	
    	if (commandObject != null) {
    		return commandObject.executeCommand(sessionId, action);
    	} else {
    		logger.info("No Action found for " + action.getAction());
    		return invalidAction.executeCommand(sessionId, action);
    	}
    }
}
