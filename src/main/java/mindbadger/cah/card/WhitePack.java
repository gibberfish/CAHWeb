package mindbadger.cah.card;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class WhitePack extends AbstractPack {
	@Autowired
	@Qualifier("allWhiteCards")
	private List<Card> allWhiteCards;

	@Override
	protected void initialisePack() {		
		pack = new ArrayList<Card>(allWhiteCards);
	}	
}
