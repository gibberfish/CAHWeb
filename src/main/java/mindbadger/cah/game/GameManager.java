package mindbadger.cah.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mindbadger.cah.sessions.PlayerSessions;

@Component
public class GameManager {
	@Autowired
	PlayerSessions players;

	@Autowired
	Map<String, GameType> gameTypes;

	private int nextGameId = 1;

	Map<String, List<Game>> gamesForType = new HashMap <String, List<Game>> ();
	Map<Integer, Game> games = new HashMap<Integer, Game> ();
	
	public GameManager () {
	}
	
	public synchronized void createNewGameAndAddFirstPlayer (String gameType, String playerName) {
		Game newGame = new Game(nextGameId, gameTypes.get(gameType));
		nextGameId++;
		newGame.addPlayerToGame(players.getPlayer(playerName));
		
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
		return new ArrayList<GameType> (this.gameTypes.values());
	}

	public Map<Integer, Game> getGames() {
		return games;
	}
}
