package mindbadger.cah.webservice;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mindbadger.gameserver.game.GameManager;
import mindbadger.gameserver.player.Player;
import mindbadger.gameserver.player.PlayerSessions;
import mindbadger.gameserver.player.PlayerState;

@RestController
public class PlayerReadyService {
	final static Logger logger = Logger.getLogger(PlayerReadyService.class);
	
	@Autowired
	PlayerSessions players;

	@Autowired
	GameManager gameManager;

	@RequestMapping(value="/player/ready", method=RequestMethod.POST)
    public void playerReady(@RequestParam(value="player") String playerName) {
		logger.info("playerReady, player: " + playerName);
		Player player = players.getPlayer(playerName);
		player.setPlayerState(PlayerState.PLAYING);
    }
}
