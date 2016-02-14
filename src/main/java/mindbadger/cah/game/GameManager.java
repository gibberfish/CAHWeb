package mindbadger.cah.game;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class GameManager {
	List<GameType> gameTypes = new ArrayList<GameType> ();
	
	public GameManager () {
		gameTypes.add(new GameType ("cardsAgainstHumanity", "Cards Against Humanity"));
		gameTypes.add(new GameType ("fluxx", "Fluxx"));
	}
	
	public List<GameType> getGameTypes () {
		return this.gameTypes;
	}
}
