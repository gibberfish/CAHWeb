package mindbadger.cardsagainsthumanity.websocket;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ServerEndpoint("/game")
public class WebSocketEndPoint {

	private PlayerSessionManager playerSessionManager;

	private String playerName = null;
	
	public WebSocketEndPoint() {
		ApplicationContext context = new ClassPathXmlApplicationContext("dispatcher-servlet.xml");
		playerSessionManager = (PlayerSessionManager) context.getBean("playerSessionManager");
		System.out.println("Got player session manager: " + playerSessionManager);
	}
	
	@OnMessage
    public void onMessage(String message, Session session) {
		System.out.println("Message from " + session.getId() + ": " + message);
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			CommandMessage messageObject = mapper.readValue(message, CommandMessage.class);

			System.out.println("Message command: " + messageObject.getCommand());
			System.out.println("Message value: " + messageObject.getValue());
			
			if ("login".equals(messageObject.getCommand())) {
				registerPlayer(messageObject.getValue(), session);
			}
			
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
		System.out.println(session.getId() + " has opened a connection. But we don't have a player at this point");		
        try {
            session.getBasicRemote().sendText("Connection Established");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
	}

	@OnClose
	public void onClose (Session session) {
		playerSessionManager.removeSession(playerName);
		System.out.println("Session " +session.getId()+" has ended (player name playerName)");
		System.out.println("We now have " + playerSessionManager.getSessions().size() + " sessions.");
	}

	private void registerPlayer (String name, Session session) {
		this.playerName = name;
		playerSessionManager.addSession(name, session);
		System.out.println(session.getId() + " has been registered with name " + name);
	}
}
