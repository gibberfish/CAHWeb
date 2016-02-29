package mindbadger.gameserver.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mindbadger.gameserver.player.Player;
import mindbadger.gameserver.player.PlayerSessions;

@Component
public class GameManager {
	final static Logger logger = Logger.getLogger(GameManager.class);
	
	@Autowired
	PlayerSessions players;

	@Autowired
	Map<String, GameFactory> gameFactorys;
	
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
		logger.info("createNewGameAndAddFirstPlayer, gameType: " + gameType + ", playerName: " + playerName);

		GameFactory gameFactory = gameFactorys.get(gameType+"GameFactory");
		
		if (gameFactory != null) {
			Game newGame = gameFactory.createGame(nextGameId);
	
			nextGameId++;
			
			Player player = players.getPlayer(playerName);
			Player gameSpecificPlayer = newGame.addPlayerToGame(player);
			gameSpecificPlayer.setGame(newGame);
			players.replacePlayerNotInGameWithGameSpecificPlayer(gameSpecificPlayer);
			
			List<Game> gamesForThisType = gamesForType.get(gameType);
			if (gamesForThisType == null) {
				gamesForThisType = new ArrayList<Game> ();
			}
			gamesForThisType.add(newGame);
			this.games.put(newGame.getGameId(), newGame);
			
			logger.info("New Game: " + this.games.get(newGame.getGameId()));
			
			this.gamesForType.put(gameType, gamesForThisType);
		} else {
			logger.error("Invalid game type: " + gameType);
		}
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
	
	public Game getGameForId (int id) {
		return this.games.get(id);
	}
}
