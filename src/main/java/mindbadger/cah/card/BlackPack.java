package mindbadger.cah.card;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class BlackPack extends AbstractPack {
	public static final List<BlackCard> allBlackCards = new ArrayList<BlackCard> ();

	@Override
	protected void initialisePack() {
		allBlackCards.add(new BlackCard ("{}? There's an app for that."));
		allBlackCards.add(new BlackCard ("Why can't I sleep at night?"));
		allBlackCards.add(new BlackCard ("What's that smell?"));
		allBlackCards.add(new BlackCard ("I got 99 problems but {} ain't one."));
		
		pack = new ArrayList<Card>(allBlackCards);
	}
}
