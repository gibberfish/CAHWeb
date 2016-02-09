package mindbadger.cah.player;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class Sessions {
	final static Logger logger = Logger.getLogger(Sessions.class);
	
	private Map<String, String> players = new HashMap<String,String> ();
	private Map<String, String> sessions = new HashMap<String,String> ();
	
	public void addPlayerSession (String name, String session) {
		
		int playersBefore = players.size();
		int sessionsBefore = sessions.size();
		
		String oldSession = players.get(name);
		if (oldSession != null && !oldSession.equals(session)) {
			logger.info("...registering a player under a new session - removing the old one first");
			sessions.remove(oldSession);
		}
		
		players.put(name, session);
		sessions.put(session, name);
		
		logger.info("Registered new player " + name + " with session " + session);
		logger.info("...[players, was="+playersBefore+",now="+players.size()+"]");
		logger.info("...[sessions, was="+sessionsBefore+",now="+sessions.size()+"]");
	}
	
	public void removePlayerWithSession (String session) {
		int playersBefore = players.size();
		int sessionsBefore = sessions.size();

		players.remove(sessions.get(session));
		sessions.remove(session);
		
		logger.info("Removed session " + session);
		logger.info("...[players, was="+playersBefore+",now="+players.size()+"]");
		logger.info("...[sessions, was="+sessionsBefore+",now="+sessions.size()+"]");
	}
	
	public String getSessionForPlayer (String name) {
		return players.get(name);
	}
	
	public String getPlayerNameForSession (String session) {
		return sessions.get(session);
	}
}
