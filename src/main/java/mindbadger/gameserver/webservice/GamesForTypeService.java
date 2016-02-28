package mindbadger.gameserver.webservice;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import mindbadger.gameserver.game.Game;
import mindbadger.gameserver.game.GameManager;
import mindbadger.gameserver.player.PlayerSessions;

@RestController
public class GamesForTypeService {
	final static Logger logger = Logger.getLogger(GamesForTypeService.class);
	
	@Autowired
	PlayerSessions players;

	@Autowired
	GameManager gameManager;

	@RequestMapping(value="/games/getForType", method=RequestMethod.GET)
    public @ResponseBody List<Game> getGamesForType(@RequestParam(value="gameType") String gameType) {
		logger.info("getGamesForType, gameType: " + gameType);
		return gameManager.getGamesForType(gameType);
    }
	
	@RequestMapping(value="/game/addPlayerToExistingGame", method=RequestMethod.POST)
    public void addPlayerToExistingGame(@RequestParam(value="gameId") String gameId, @RequestParam(value="player") String player) {
		logger.info("addPlayerToExistingGame, gameId: " + gameId + ", player: " + player);
		Game game = gameManager.getGameForId(Integer.parseInt(gameId));
		logger.info("..got game: " + game);
		if (game != null) {
			game.addPlayerToGame(players.getPlayer(player));
		}
    }

	@RequestMapping(value="/game/addPlayerToNewGame", method=RequestMethod.POST)
    public void addPlayerToNewGame(@RequestParam(value="gameType") String gameType, @RequestParam(value="player") String player) {
		logger.info("addPlayerToNewGame, gameType: " + gameType + ", player: " + player);
		gameManager.createNewGameAndAddFirstPlayer(gameType, player);
    }
}