package mindbadger.gameserver.websocket;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

import mindbadger.gameserver.player.PlayerSessions;

@Component
public class StompConnectEvent implements ApplicationListener<SessionConnectEvent> {
	final static Logger logger = Logger.getLogger(StompConnectEvent.class);

	@Autowired
	PlayerSessions sessions;

	@Autowired
	StompHeaderAccessorWrapper stompHeaderAccessorWrapper;
	
	/*
	 * Get the name once and associate this with the session 
	 */
	@EventListener
    public void onApplicationEvent(SessionConnectEvent event) {
        StompHeaderAccessor sha = stompHeaderAccessorWrapper.wrap(event.getMessage());
 
        String  name = sha.getNativeHeader("name").get(0);
        String sessionId = sha.getSessionId();
        sessions.addPlayerSession(name, sessionId);
        
		logger.info("Connect event [sessionId: " + sessionId +"; name: "+ name + " ]");
    }
}
