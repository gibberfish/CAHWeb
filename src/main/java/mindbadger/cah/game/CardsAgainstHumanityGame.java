package mindbadger.cah.game;

import mindbadger.cah.card.BlackPack;
import mindbadger.cah.card.WhitePack;
import mindbadger.cah.players.CardsAgainstHumanityPlayer;
import mindbadger.cah.players.Player;

public class CardsAgainstHumanityGame extends Game {

	private WhitePack whitePack;
	private BlackPack blackPack;
	
	//TODO Would be better if Spring could use a factory to create the packs, rather than instantiate here.
	public CardsAgainstHumanityGame(Integer gameId, GameType gameType) {
		super(gameId, gameType);
		
		whitePack = new WhitePack ();
		blackPack = new BlackPack ();
	}

	@Override
	public Player createGameSpecificPlayer (Player playerNotInGame) {
		return new CardsAgainstHumanityPlayer(playerNotInGame);
	}

	public WhitePack getWhitePack() {
		return whitePack;
	}

	public BlackPack getBlackPack() {
		return blackPack;
	}

}
