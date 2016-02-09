package mindbadger.cah.card;

import java.util.ArrayList;
import java.util.List;

public class BlackPack {
	private List<BlackCard> allBlackCards = new ArrayList<BlackCard> ();
	private List<BlackCard> pack;
	
	public BlackPack () {
		initialisePack();
	}

	private void initialisePack() {
		allBlackCards.add(new BlackCard ("{}? There's an app for that."));
		allBlackCards.add(new BlackCard ("Why can't I sleep at night?"));
		allBlackCards.add(new BlackCard ("What's that smell?"));
		allBlackCards.add(new BlackCard ("I got 99 problems but {} ain't one."));
		
		pack = new ArrayList<BlackCard>(allBlackCards);
	}
}
