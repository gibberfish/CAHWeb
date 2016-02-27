package mindbadger.cah.player;

import java.util.ArrayList;
import java.util.List;

import mindbadger.cah.card.Card;
import mindbadger.gameserver.player.Player;
import mindbadger.gameserver.player.PlayerInGame;

public class CardsAgainstHumanityPlayer extends PlayerInGame {

	private List<Card> hand = new ArrayList<Card> ();
	private List<Card> cardsWon = new ArrayList<Card> ();
	
	public CardsAgainstHumanityPlayer(Player playerNotInGame) {
		super(playerNotInGame);
	}

	public List<Card> getHand() {
		return this.hand;
	}

	public List<Card> getCardsWon() {
		return this.cardsWon;
	}

	public Card drawCardFromCardsWon() {
		if (cardsWon.size() == 0) {
			throw new IllegalStateException("There are no black cards to draw from");
		}
		
		Card cardDrawn = cardsWon.get(0);
		cardsWon.remove(0);
		return cardDrawn;
	}

	public void addCardToCardsWon(Card Card) {
		cardsWon.add(Card);
	}

	public void dealCardToPlayer(Card Card) {
		hand.add(Card);
	}
}
