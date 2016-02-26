package mindbadger.cah.game;

import mindbadger.cah.players.FluxxPlayer;
import mindbadger.cah.players.Player;

public class FluxxGame extends Game {

	public FluxxGame(Integer gameId, GameType gameType) {
		super(gameId, gameType);
	}

	@Override
	public Player createGameSpecificPlayer(Player playerNotInGame) {
		return new FluxxPlayer(playerNotInGame);
	}

}
