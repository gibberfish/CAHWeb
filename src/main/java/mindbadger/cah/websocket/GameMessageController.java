package mindbadger.cah.websocket;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import mindbadger.cah.player.Players;
import mindbadger.cah.websocket.pojo.GameStateChange;
import mindbadger.cah.websocket.pojo.PlayerActionCommand;

@Controller
public class GameMessageController {
	final static Logger logger = Logger.getLogger(GameMessageController.class);
	
	@Autowired
	private Players players;
	
    @MessageMapping("/gameserver")
    @SendTo("/gamestate/gameStateUpdates")
    public GameStateChange handlePlayerActionMessage(SimpMessageHeaderAccessor headerAccessor, @Payload PlayerActionCommand command) throws Exception {
    	String playerNameForSession = players.getPlayerNameForSession(headerAccessor.getSessionId());
    	
    	/*
    	 *  We need to put something in here that takes the incoming payload supplied by the front-end
    	 *  and then map this into a class that will execute the requested command
    	 *  e.g. join, leave, drawCard, playCard, selectCard
    	 */
    	
    	
    	GameStateChange gar = new GameStateChange();
		gar.setPlayer(playerNameForSession);
    	gar.setCommand(command.getCommand());
    	gar.setValue(command.getValue());
    	
    	//logger.info("Created new response in handlePlayerActionMessage in object " + this);
    	
        return gar;
    }
}
