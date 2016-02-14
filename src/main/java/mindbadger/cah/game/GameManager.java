package mindbadger.cah.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class GameManager {
	Map<String, GameType> gameTypes = new HashMap<String, GameType> ();
	private int nextGameId = 1;
	
	// keyed on GameType 'type'
	Map<String, List<Game>> gamesForType = new HashMap <String, List<Game>> ();
	
	Map<Integer, Game> games = new HashMap<Integer, Game> ();
	
	public GameManager () {
		gameTypes.put("cardsAgainstHumanity", new GameType ("cardsAgainstHumanity", "Cards Against Humanity", "cah.html"));
		gameTypes.put("fluxx", new GameType ("fluxx", "Fluxx", "fluxx.html"));
	}
	
	public synchronized void createNewGameAndAddFirstPlayer (String gameType, String playerName) {
		Game newGame = new Game(nextGameId, gameTypes.get(gameType));
		nextGameId++;
		newGame.addPlayerToGame(playerName);
		
		List<Game> gamesForThisType = gamesForType.get(gameType);
		if (gamesForThisType == null) {
			gamesForThisType = new ArrayList<Game> ();
		}
		gamesForThisType.add(newGame);
		games.put(newGame.getGameId(), newGame);
		
		this.gamesForType.put(gameType, gamesForThisType);
	}
	
	public List<Game> getGamesForType (String gameType) {
		return gamesForType.get(gameType);
	}
	
	public List<GameType> getGameTypes () {
		return new ArrayList<GameType> (this.gameTypes.values());
	}

	public Map<Integer, Game> getGames() {
		return games;
	}
}
