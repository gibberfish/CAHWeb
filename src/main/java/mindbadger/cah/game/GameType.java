package mindbadger.cah.game;

public class GameType {
	private String type;
	private String displayName;
	private String gamePage;
	private String clazz;
	
	public GameType (String type, String displayName, String gamePage, String clazz) {
		this.type = type;
		this.displayName = displayName;
		this.gamePage = gamePage;
		this.clazz = clazz;
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
	public String getClazz() {
		return clazz;
	}
}
