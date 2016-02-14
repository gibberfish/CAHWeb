package mindbadger.cah.game;

public class GameType {
	private String type;
	private String displayName;
	
	public GameType (String type, String displayName) {
		this.type = type;
		this.displayName = displayName;
	}
	
	public String getType() {
		return type;
	}
	public String getDisplayName() {
		return displayName;
	}
}
