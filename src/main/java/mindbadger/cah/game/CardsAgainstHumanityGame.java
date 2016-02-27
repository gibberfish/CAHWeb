package mindbadger.cah.game;

import mindbadger.cah.card.PackOfCards;
import mindbadger.cah.players.CardsAgainstHumanityPlayer;
import mindbadger.cah.players.Player;

public class CardsAgainstHumanityGame extends Game {

	private PackOfCards whitePack;
	private PackOfCards blackPack;
	
	public CardsAgainstHumanityGame(Integer gameId, GameType gameType) {
		super(gameId, gameType);
	}

	@Override
	public Player createGameSpecificPlayer (Player playerNotInGame) {
		return new CardsAgainstHumanityPlayer(playerNotInGame);
	}

	
	public PackOfCards getWhitePack() {
		return whitePack;
	}

	public void setWhitePack(PackOfCards whitePack) {
		this.whitePack = whitePack;
	}

	public PackOfCards getBlackPack() {
		return blackPack;
	}

	public void setBlackPack(PackOfCards blackPack) {
		this.blackPack = blackPack;
	}
}
