package mindbadger.cah.websocket;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import mindbadger.cah.action.Action;
import mindbadger.cah.sessions.Sessions;
import mindbadger.cah.websocket.pojo.GameStateChange;
import mindbadger.cah.websocket.pojo.PlayerAction;

@Controller
public class GameMessageController {
	final static Logger logger = Logger.getLogger(GameMessageController.class);
	
	@Autowired
	Sessions players;

	@Autowired
	Map<String, Action> actions;
	
    @MessageMapping("/gameserver")
    @SendTo("/gamestate/gameStateUpdates")
    public GameStateChange handlePlayerActionMessage(SimpMessageHeaderAccessor headerAccessor, @Payload PlayerAction action) throws Exception {
    	logger.info("Received Player Action Message: " + action.getAction());

    	String sessionId = headerAccessor.getSessionId();
		String playerNameForSession = players.getPlayerNameForSession(sessionId);
    	
    	Action commandObject = actions.get(action.getAction());
    	
    	if (commandObject != null) {
    		return commandObject.executeCommand(sessionId, playerNameForSession, action);
    	} else {
    		logger.info("No Action found for " + action.getAction());
    		GameStateChange gsc = new GameStateChange();
    		gsc.setPlayer(playerNameForSession);
    		gsc.setCommand(action.getAction());
    		gsc.setValue("ACTION NOT FOUND");
    		return gsc;
    	}
    }
}
