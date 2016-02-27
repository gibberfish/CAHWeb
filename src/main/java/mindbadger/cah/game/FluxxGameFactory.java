package mindbadger.cah.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import mindbadger.gameserver.game.Game;
import mindbadger.gameserver.game.GameFactory;
import mindbadger.gameserver.game.GameType;

@Component("fluxxGameFactory")
public class FluxxGameFactory implements GameFactory{
	
	@Autowired
	@Qualifier ("fluxxGameType")
	private GameType gameType;

	@Override
	public Game createGame(int gameId) {
		return new FluxxGame(gameId, gameType);
	}

}
