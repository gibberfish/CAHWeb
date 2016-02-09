package mindbadger.cah.card;

import java.util.ArrayList;
import java.util.List;

public class WhitePack {
	private List<WhiteCard> allWhiteCards = new ArrayList<WhiteCard> ();
	private List<WhiteCard> pack;
	
	public WhitePack () {
		initialisePack();
	}

	private void initialisePack() {
		allWhiteCards.add(new WhiteCard ("Flying sex snakes."));
		allWhiteCards.add(new WhiteCard ("Michelle Obama's arms."));
		allWhiteCards.add(new WhiteCard ("German dungeon porn."));
		allWhiteCards.add(new WhiteCard ("White people."));
		
		pack = new ArrayList<WhiteCard>(allWhiteCards);
	}
}
