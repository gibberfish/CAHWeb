package mindbadger.cah.sessions;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import mindbadger.cah.game.Player;

@Component
public class PlayerSessions {
	final static Logger logger = Logger.getLogger(PlayerSessions.class);
	
	private Map<String, String> sessionsKeyedOnPlayerName = new HashMap<String,String> ();
	private Map<String, Player> playerskeyedOnSessionId = new HashMap<String,Player> ();
	private Map<String, Player> playerskeyedOnPlayerName = new HashMap<String,Player> ();
	
	public void addPlayerSession (String name, String session) {
		
		int sessionsBefore = sessionsKeyedOnPlayerName.size();
		int playersBefore = playerskeyedOnSessionId.size();
		
		String oldSession = sessionsKeyedOnPlayerName.get(name);
		if (oldSession != null && !oldSession.equals(session)) {
			logger.info("...registering a player under a new session - removing the old one first");
			playerskeyedOnSessionId.remove(oldSession);
		}
		
		
		
		
		Player player = playerskeyedOnPlayerName.get(name);
		if (player == null) {
			player = new Player();
			player.setName(name);
			playerskeyedOnPlayerName.put(name, player);
		}
		
		
		
		
		sessionsKeyedOnPlayerName.put(name, session);
		playerskeyedOnSessionId.put(session, player);
		
		logger.info("Registered new player " + name + " with session " + session);
		logger.info("...[sessions, was="+sessionsBefore+",now="+sessionsKeyedOnPlayerName.size()+"]");
		logger.info("...[players, was="+playersBefore+",now="+playerskeyedOnSessionId.size()+"]");
	}
	
	public void removePlayerWithSession (String session) {
		int sessionsBefore = sessionsKeyedOnPlayerName.size();
		int playersBefore = playerskeyedOnSessionId.size();

		Player player = playerskeyedOnSessionId.get(session);
		sessionsKeyedOnPlayerName.remove(player.getName());
		playerskeyedOnSessionId.remove(session);
		playerskeyedOnPlayerName.remove(player.getName());
		
		logger.info("Removed session " + session);
		logger.info("...[sessions, was="+playersBefore+",now="+playerskeyedOnSessionId.size()+"]");
		logger.info("...[players, was="+sessionsBefore+",now="+sessionsKeyedOnPlayerName.size()+"]");
	}
	
	public String getSessionForPlayer (String name) {
		return sessionsKeyedOnPlayerName.get(name);
	}
	
	public Player getPlayerNameForSession (String session) {
		return playerskeyedOnSessionId.get(session);
	}
}
