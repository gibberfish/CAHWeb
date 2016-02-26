package mindbadger.cah.players;

import java.util.ArrayList;
import java.util.List;

import mindbadger.cah.card.BlackCard;
import mindbadger.cah.card.WhiteCard;

public class CardsAgainstHumanityPlayer extends PlayerInGame {

	private List<WhiteCard> hand = new ArrayList<WhiteCard> ();
	private List<BlackCard> cardsWon = new ArrayList<BlackCard> ();
	
	public CardsAgainstHumanityPlayer(Player playerNotInGame) {
		super(playerNotInGame);
	}

	public List<WhiteCard> getHand() {
		return this.hand;
	}

	public List<BlackCard> getCardsWon() {
		return this.cardsWon;
	}

	public BlackCard drawCardFromCardsWon() {
		if (cardsWon.size() == 0) {
			throw new IllegalStateException("There are no black cards to draw from");
		}
		
		BlackCard cardDrawn = cardsWon.get(0);
		cardsWon.remove(0);
		return cardDrawn;
	}

	public void addBlackCardToCardsWon(BlackCard blackCard) {
		cardsWon.add(blackCard);
	}

	public void dealWhiteCardToPlayer(WhiteCard whiteCard) {
		hand.add(whiteCard);
	}
}
