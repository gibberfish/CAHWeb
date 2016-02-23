package mindbadger.cah.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mindbadger.cah.sessions.PlayerSessions;

@Component
public class GameManager {
	final static Logger logger = Logger.getLogger(GameManager.class);
	
	@Autowired
	PlayerSessions players;

	Map<String, GameType> gameTypes = new HashMap<String, GameType>();

	private int nextGameId = 1;

	Map<String, List<Game>> gamesForType = new HashMap <String, List<Game>> ();
	Map<Integer, Game> games = new HashMap<Integer, Game> ();
	
	@Autowired
	public GameManager (Set<GameType> gameTypes) {
		for (GameType gameType : gameTypes) {
			this.gameTypes.put(gameType.getType(), gameType);
		}
	}
	
	public synchronized void createNewGameAndAddFirstPlayer (String gameType, String playerName) {
		GameType gameTypeObject = gameTypes.get(gameType);
		
		logger.info("createNewGameAndAddFirstPlayer, gameType: " + gameType + ", playerName: " + playerName);
		
		Game newGame = new Game(nextGameId, gameTypeObject);
		nextGameId++;
		
		Player player = players.getPlayer(playerName);
		newGame.addPlayerToGame(player);
		player.setGame(newGame);
		
		List<Game> gamesForThisType = gamesForType.get(gameType);
		if (gamesForThisType == null) {
			gamesForThisType = new ArrayList<Game> ();
		}
		gamesForThisType.add(newGame);
		games.put(newGame.getGameId(), newGame);
		
		this.gamesForType.put(gameType, gamesForThisType);
	}
	
	public List<Game> getGamesForType (String gameType) {
		List<Game> gamesOfType = gamesForType.get(gameType);
		if (gamesOfType == null) {
			return new ArrayList<Game> ();
		} else {
			return gamesOfType;
		}
	}
	
	public List<GameType> getGameTypes () {
		logger.info("gameTypes: " + gameTypes.keySet().toString());
		
		return new ArrayList<GameType> (this.gameTypes.values());
	}

	public Map<Integer, Game> getGames() {
		return games;
	}
}
