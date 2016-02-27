package mindbadger.fluxx;

import mindbadger.gameserver.game.Game;
import mindbadger.gameserver.game.GameType;
import mindbadger.gameserver.player.Player;

public class FluxxGame extends Game {

	public FluxxGame(Integer gameId, GameType gameType) {
		super(gameId, gameType);
	}

	@Override
	public Player createGameSpecificPlayer(Player playerNotInGame) {
		return new FluxxPlayer(playerNotInGame);
	}

	@Override
	public int getMinimumPlayers() {
		return 2;
	}

}
