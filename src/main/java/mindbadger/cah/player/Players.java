package mindbadger.cah.player;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class Players {
	final static Logger logger = Logger.getLogger(Players.class);
	
	private Map<String, String> players = new HashMap<String,String> ();
	private Map<String, String> sessions = new HashMap<String,String> ();
	
	public void addPlayer (String name, String session) {
		players.put(name, session);
		sessions.put(session, name);
		logger.info("Registered new player " + name + " with session " + session);
	}
	
	public void removePlayerWithSession (String session) {
		players.remove(sessions.get(session));
		sessions.remove(session);
		logger.info("Removed session " + session);
	}
	
	public String getSessionForPlayer (String name) {
		return players.get(name);
	}
	
	public String getPlayerNameForSession (String session) {
		return sessions.get(session);
	}
}
