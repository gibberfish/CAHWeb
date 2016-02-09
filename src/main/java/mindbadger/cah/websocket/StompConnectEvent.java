package mindbadger.cah.websocket;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

import mindbadger.cah.player.Players;

@Component
public class StompConnectEvent implements ApplicationListener<SessionConnectEvent> {
	final static Logger logger = Logger.getLogger(StompConnectEvent.class);

	@Autowired
	private Players players;

	@EventListener
    public void onApplicationEvent(SessionConnectEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
 
        String  name = sha.getNativeHeader("name").get(0);
        logger.info("Connect event [sessionId: " + sha.getSessionId() +"; name: "+ name + " ]");
        logger.info(event);
        
        players.addPlayer(name, sha.getSessionId());
    }
}

