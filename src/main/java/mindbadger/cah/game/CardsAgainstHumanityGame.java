package mindbadger.cah.game;

import com.fasterxml.jackson.annotation.JsonIgnore;

import mindbadger.cah.card.PackOfCards;
import mindbadger.cah.player.CardsAgainstHumanityPlayer;
import mindbadger.gameserver.game.Game;
import mindbadger.gameserver.game.GameType;
import mindbadger.gameserver.player.Player;

public class CardsAgainstHumanityGame extends Game {

	@JsonIgnore
	private PackOfCards whitePack;
	
	@JsonIgnore
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

	@Override
	public int getMinimumPlayers() {
		return 3;
	}
}
