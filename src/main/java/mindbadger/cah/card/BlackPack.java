package mindbadger.cah.card;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class BlackPack extends AbstractPack {
	
	@Autowired
	@Qualifier("allBlackCards")
	private List<Card> allBlackCards;

	@Override
	protected void initialisePack() {		
		pack = new ArrayList<Card>(allBlackCards);
	}	
}
