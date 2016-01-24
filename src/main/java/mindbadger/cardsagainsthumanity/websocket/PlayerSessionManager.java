package mindbadger.cardsagainsthumanity.websocket;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.Session;

public class PlayerSessionManager {
	// A map containing all of the sessions, keyed by player name
	private static final Map<String, Session> sessions = Collections.synchronizedMap(new HashMap<String, Session> ());
	
	public Collection<Session> getSessions () {
		Collection<Session> values = sessions.values();
		values.remove(null);
		return values;
	}
	
	public void addSession (String name, Session session) {
		sessions.put(name, session);
	}
	
	public void removeSession (String name) {
		sessions.put(name, null);
	}
}
