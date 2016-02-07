package mindbadger.cah.websocket;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import mindbadger.cah.player.Players;

@Component
public class StompDisconnectEvent implements ApplicationListener<SessionDisconnectEvent> {
	final static Logger logger = Logger.getLogger(StompDisconnectEvent.class);

	@Autowired
	private Players players;

	@EventListener
    public void onApplicationEvent(SessionDisconnectEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        logger.info("Disconnect event [sessionId: " + sha.getSessionId() + " ]");
        players.removePlayerWithSession(sha.getSessionId());
    }
}

