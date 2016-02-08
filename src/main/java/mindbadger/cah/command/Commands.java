package mindbadger.cah.command;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Commands {
	private Map<String, Command> commands = new HashMap<String, Command> ();
	
	@Autowired
	@Resource(name="login")
	private Command loginCommand;

	@Autowired
	@Resource(name="logout")
	private Command logoutCommand;

	@PostConstruct
	public void init() {
		commands.put("login", loginCommand);
		commands.put("logout", logoutCommand);
	}
	
	public Command getCommand(String command) {
		return commands.get(command);
	}
}
