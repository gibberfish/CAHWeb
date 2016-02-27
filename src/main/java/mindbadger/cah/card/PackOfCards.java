package mindbadger.cah.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class PackOfCards {
	protected List<Card> pack;

	public PackOfCards (List<Card> cards) {
		pack = new ArrayList<Card>(cards);
	}
	
	public List<Card> getPack () {
		return this.pack;
	}

	public void shuffle() {
		Collections.shuffle(pack, new Random((new Date()).getTime()));
	}
	
	public Card dealCard() {
		Card dealtCard = pack.get(0);
		pack.remove(0);
		return dealtCard;
	}

	public void returnCard(Card newCard) {
		pack.add(newCard);
	}
}
