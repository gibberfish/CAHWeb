package mindbadger.cah.game;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import mindbadger.cah.card.Card;
import mindbadger.cah.card.PackOfCards;
import mindbadger.gameserver.game.Game;
import mindbadger.gameserver.game.GameFactory;
import mindbadger.gameserver.game.GameType;

@Component("cardsAgainstHumanityGameFactory")
public class CardsAgainstHumanityGameFactory implements GameFactory{
	@Autowired
	@Qualifier ("cardsAgainstHumanityGameType")
	private GameType gameType;
	
	@Resource(name="allWhiteCards")
	protected List<Card> allWhiteCards;

	@Resource(name="allBlackCards")
	protected List<Card> allBlackCards;

	@Override
	public Game createGame(int gameId) {
		CardsAgainstHumanityGame game = new CardsAgainstHumanityGame(gameId, gameType);
		
		PackOfCards whitePack = new PackOfCards(allWhiteCards);
		game.setWhitePack(whitePack);

		PackOfCards blackPack = new PackOfCards(allBlackCards);
		game.setBlackPack(blackPack);

		return game;
	}
}
