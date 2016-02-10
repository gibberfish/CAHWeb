package mindbadger.cah.card;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class WhitePack extends AbstractPack {
	public static final List<WhiteCard> allWhiteCards = new ArrayList<WhiteCard> ();
	
	@Override
	protected void initialisePack() {
		allWhiteCards.add(new WhiteCard ("Flying sex snakes."));
		allWhiteCards.add(new WhiteCard ("Michelle Obama's arms."));
		allWhiteCards.add(new WhiteCard ("German dungeon porn."));
		allWhiteCards.add(new WhiteCard ("White people."));
		pack = new ArrayList<Card>(allWhiteCards);
	}
}
