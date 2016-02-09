package mindbadger.cah.websocket;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import mindbadger.cah.command.Command;
import mindbadger.cah.player.Players;
import mindbadger.cah.websocket.pojo.GameStateChange;
import mindbadger.cah.websocket.pojo.PlayerActionCommand;

@Controller
public class GameMessageController {
	final static Logger logger = Logger.getLogger(GameMessageController.class);
	
	@Autowired
	private Players players;

	@Autowired
	private Map<String, Command> commandList;
	
    @MessageMapping("/gameserver")
    @SendTo("/gamestate/gameStateUpdates")
    public GameStateChange handlePlayerActionMessage(SimpMessageHeaderAccessor headerAccessor, @Payload PlayerActionCommand command) throws Exception {
    	
    	for (String s : commandList.keySet()) {
    		logger.info("Autowired key : " + s);
    	}
    	
    	for (Command c : commandList.values()) {
    		logger.info("Autowired value : " + c);
    	}
    	
    	String playerNameForSession = players.getPlayerNameForSession(headerAccessor.getSessionId());
    	//String playerNameForSession = headerAccessor.getNativeHeader("name").get(0);
    	/*
    	 *  We need to put something in here that takes the incoming payload supplied by the front-end
    	 *  and then map this into a class that will execute the requested command
    	 *  e.g. join, leave, drawCard, playCard, selectCard
    	 */
    	logger.info("Received Player Action Message: " + command.getCommand());
    	
    	//Command commandObject = commands.getCommand(command.getCommand());
    	Command commandObject = commandList.get(command.getCommand());
    	
    	
    	logger.info("command object: " + commandObject);
    	
		GameStateChange gar = commandObject.executeCommand(headerAccessor.getSessionId(), playerNameForSession, command);
    	
//    	GameStateChange gar = new GameStateChange();
//		gar.setPlayer(playerNameForSession);
//    	gar.setCommand(command.getCommand());
//    	gar.setValue(command.getValue());
    	
    	//logger.info("Created new response in handlePlayerActionMessage in object " + this);
    	
        return gar;
    }
}
