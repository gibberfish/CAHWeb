package mindbadger.cah.card;

import org.apache.commons.lang.StringUtils;

public class Card {
	private static final String TOKEN = "{}";
	
	private CardType type;
	private String words;
	
	public Card (CardType type, String words) {
		this.type = type;
		this.words = words;
	}
	
	public CardType getType() {
		return type;
	}
	
	public String getWords() {
		return words;
	}
	
	public String getBackOfCardWords() {
		return words.replace(TOKEN, "_________");
	}
	
	public int getTokens () {
		return StringUtils.countMatches(words, TOKEN);
	}
}
