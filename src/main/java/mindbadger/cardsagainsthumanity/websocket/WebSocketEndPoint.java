package mindbadger.cardsagainsthumanity.websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ServerEndpoint(value="/game", configurator = SpringConfigurator.class)
public class WebSocketEndPoint {

	final static Logger logger = Logger.getLogger(WebSocketEndPoint.class);

	@Autowired
//	@Resource(name="playerSessionManager")
	private PlayerSessionManager playerSessionManager;
	
	private String name;
	
	// A set containing all of the sessions
	//private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());
	
	@OnMessage
    public void onMessage(String message, Session session) {
		logger.info("Message from " + session.getId() + ": " + message);

		ObjectMapper mapper = new ObjectMapper();
		
		try {
			CommandMessage messageObject = mapper.readValue(message, CommandMessage.class);

			logger.info("Message command: " + messageObject.getCommand());
			logger.info("Message value: " + messageObject.getValue());			
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Session s : playerSessionManager.getSessions()){
	        try {
	            s.getBasicRemote().sendText(session.getId() + " says: " + message);
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
		}
	}
	
	@OnOpen
	public void onOpen (Session session) {
		logger.info(session.getId() + " has opened a connection");
		
		//sessions.add(session);
		//logger.info("We now have " + sessions.size() + " sessions. " + this);
		
        try {
            session.getBasicRemote().sendText("Connection Established");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
	}

	@OnClose
	public void onClose (Session session) {
		//sessions.remove(session);
		
		logger.info("Session " +session.getId()+" has ended");
		//logger.info("We now have " + sessions.size() + " sessions. " + this);
	}

}
