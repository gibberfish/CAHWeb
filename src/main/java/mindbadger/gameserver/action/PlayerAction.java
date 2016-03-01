package mindbadger.gameserver.action;

import java.util.Map;

public class PlayerAction {

    private String action;
    private Map<String, Object> parameters;
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Map<String, Object> getParameters() {
		return parameters;
	}
	public void setValue(Map<String, Object> parameters) {
		this.parameters = parameters;
	}
}
