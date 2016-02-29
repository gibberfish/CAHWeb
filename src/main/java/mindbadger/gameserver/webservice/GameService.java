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
import mindbadger.gameserver.game.GameType;
import mindbadger.gameserver.player.Player;
import mindbadger.gameserver.player.PlayerSessions;

@RestController
public class GameService {
	final static Logger logger = Logger.getLogger(GameService.class);
	
	@Autowired
	PlayerSessions players;

	@Autowired
	GameManager gameManager;

	@RequestMapping(value="/games/getForType", method=RequestMethod.GET)
    public @ResponseBody List<Game> getGamesForType(@RequestParam(value="gameType") String gameType) {
		logger.info("getGamesForType, gameType: " + gameType);
		return gameManager.getGamesForType(gameType);
    }
	
	//TODO Take logic out of this service and add to a new method in the game manager
	@RequestMapping(value="/game/addPlayerToExistingGame", method=RequestMethod.POST)
    public void addPlayerToExistingGame(@RequestParam(value="gameId") String gameId, @RequestParam(value="player") String player) {
		logger.info("addPlayerToExistingGame, gameId: " + gameId + ", player: " + player);
		Game game = gameManager.getGameForId(Integer.parseInt(gameId));
		logger.info("..got game: " + game);
		if (game != null) {
			Player playerInGame = game.addPlayerToGame(players.getPlayer(player));
			playerInGame.setGame(game);
			players.replacePlayerNotInGameWithGameSpecificPlayer(playerInGame);

		}
    }

	@RequestMapping(value="/game/addPlayerToNewGame", method=RequestMethod.POST)
    public void addPlayerToNewGame(@RequestParam(value="gameType") String gameType, @RequestParam(value="player") String player) {
		logger.info("addPlayerToNewGame, gameType: " + gameType + ", player: " + player);
		gameManager.createNewGameAndAddFirstPlayer(gameType, player);
    }
	
	@RequestMapping(value="/game/type/getAll", method=RequestMethod.GET)
    public @ResponseBody List<GameType> getGameTypes() {
        return gameManager.getGameTypes();
    }
}
