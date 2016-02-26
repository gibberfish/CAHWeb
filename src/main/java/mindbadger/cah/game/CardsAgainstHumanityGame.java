package mindbadger.cah.game;

import mindbadger.cah.players.CardsAgainstHumanityPlayer;
import mindbadger.cah.players.Player;

public class CardsAgainstHumanityGame extends Game {

	public CardsAgainstHumanityGame(Integer gameId, GameType gameType) {
		super(gameId, gameType);
	}

	@Override
	public Player createGameSpecificPlayer (Player playerNotInGame) {
		return new CardsAgainstHumanityPlayer(playerNotInGame);
	}

}
