package mindbadger.cah.game;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mindbadger.cah.players.Player;
import mindbadger.cah.players.PlayerSessions;

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
		
//		Game newGame = new Game(nextGameId, gameTypeObject);
		Class<?>[] type = { Integer.class, GameType.class };
		Class<?> myClass;
		Game newGame = null;
		try {
			myClass = Class.forName("mindbadger.cah.game."+gameTypeObject.getClazz());
			Constructor<?> cons = myClass.getConstructor(type);
			Object[] obj = { nextGameId, gameTypeObject};
			newGame = (Game) cons.newInstance(obj);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		nextGameId++;
		
		Player player = players.getPlayer(playerName);
		newGame.addPlayerToGame(player);
		player.setGame(newGame);
		
		List<Game> gamesForThisType = gamesForType.get(gameType);
		if (gamesForThisType == null) {
			gamesForThisType = new ArrayList<Game> ();
		}
		gamesForThisType.add(newGame);
		this.games.put(newGame.getGameId(), newGame);
		
		logger.info("New Game: " + this.games.get(newGame.getGameId()));
		
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
	
	public Game getGameForId (int id) {
		return this.games.get(id);
	}
}
