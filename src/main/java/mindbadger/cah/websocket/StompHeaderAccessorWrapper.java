package mindbadger.cah.websocket;

import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;

@Component
public class StompHeaderAccessorWrapper {

	public StompHeaderAccessor wrap(Message<byte[]> message) {
		return StompHeaderAccessor.wrap(message);
	}

}
