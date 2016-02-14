package mindbadger.cah.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class GameManager {
	List<GameType> gameTypes = new ArrayList<GameType> ();
	
	Map<GameType, List<Game>> gamesForType = new HashMap <GameType, List<Game>> ();
	
	public GameManager () {
		gameTypes.add(new GameType ("cardsAgainstHumanity", "Cards Against Humanity", "cah.html"));
		gameTypes.add(new GameType ("fluxx", "Fluxx", "fluxx.html"));
	}
	
	public List<GameType> getGameTypes () {
		return this.gameTypes;
	}
}
