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
import mindbadger.cah.game.GameStateChange;
import mindbadger.cah.game.PlayerAction;
import mindbadger.cah.sessions.PlayerSessions;

@Controller
public class GameMessageController {
	final static Logger logger = Logger.getLogger(GameMessageController.class);
	
	@Autowired
	Map<String, Action> actions;
	
    @MessageMapping("/gameserver")
    @SendTo("/gamestate/gameStateUpdates")
    public GameStateChange handlePlayerActionMessage(SimpMessageHeaderAccessor headerAccessor, @Payload PlayerAction action) throws Exception {
    	logger.info("Received Player Action Message: " + action.getAction());

    	//TODO Consistent protocol for the websocket messages:
    	/*
    	 * Input: PlayerAction (action & value)
    	 * (implicit input is the session id, from which we can identify the player)
    	 * 
    	 * Action is executed.
    	 * 
    	 * Action returns a GameStateChange
    	 * This includes:
    	 * - The 
    	 * 
    	 * 
    	 * The controller should only have sight of the session id and the command that has come in (not the player).
    	 * The action should be an abstract class that deals with getting the player name from the session.
    	 * The response should always contain the Player Name and the Game to which the message is associated.
    	 * (clients can then choose to ignore messages that are not related to their game)
    	 * 
    	 */
    	String sessionId = headerAccessor.getSessionId();
    	Action commandObject = actions.get(action.getAction());
    	
    	if (commandObject != null) {
    		return commandObject.executeCommand(sessionId, action);
    	} else {
    		logger.info("No Action found for " + action.getAction());
    		GameStateChange gsc = new GameStateChange();
    		gsc.setPlayer(null);
    		gsc.setCommand(action.getAction());
    		gsc.setValue("ACTION NOT FOUND (session " + sessionId + ")");
    		return gsc;
    	}
    }
}
