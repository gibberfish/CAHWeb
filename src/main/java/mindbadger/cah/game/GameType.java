package mindbadger.cah.game;

public class GameType {
	private String type;
	private String displayName;
	private String gamePage;
	
	public GameType (String type, String displayName, String gamePage) {
		this.type = type;
		this.displayName = displayName;
		this.gamePage = gamePage;
	}
	
	public String getType() {
		return type;
	}
	public String getDisplayName() {
		return displayName;
	}
	public String getGamePage() {
		return gamePage;
	}
}
