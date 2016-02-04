package hello;

import org.apache.log4j.Logger;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class WebsocketHandler extends TextWebSocketHandler {
	final static Logger logger = Logger.getLogger(WebsocketHandler.class);
	
	@Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        logger.info("Received a text message: " + message);
    }
}
