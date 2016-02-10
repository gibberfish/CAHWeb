package mindbadger.cah.card;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

/*
 * The pack is a list 
 */
public abstract class AbstractPack {
	protected List<Card> pack;

	public AbstractPack () {
		initialisePack();
	}
	
	protected abstract void initialisePack ();
	
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
