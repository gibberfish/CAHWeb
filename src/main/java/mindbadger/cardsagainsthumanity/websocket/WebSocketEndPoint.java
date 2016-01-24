package mindbadger.cardsagainsthumanity.websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.websocket.*;
import javax.websocket.server.*;

@ServerEndpoint("/game")
public class WebSocketEndPoint {

	// A set containing all of the sessions
	private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());
	
	@OnMessage
    public void onMessage(String message, Session session) {
		System.out.println("Message from " + session.getId() + ": " + message);
		
		for(Session s : sessions){
	        try {
	            s.getBasicRemote().sendText(session.getId() + " says: " + message);
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
		}
	}
	
	@OnOpen
	public void onOpen (Session session) {
		System.out.println(session.getId() + " has opened a connection");
		
		sessions.add(session);
		System.out.println("We now have " + sessions.size() + " sessions. " + this);
		
        try {
            session.getBasicRemote().sendText("Connection Established");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
	}

	@OnClose
	public void onClose (Session session) {
		sessions.remove(session);
		
		System.out.println("Session " +session.getId()+" has ended");
		System.out.println("We now have " + sessions.size() + " sessions. " + this);
	}

}
