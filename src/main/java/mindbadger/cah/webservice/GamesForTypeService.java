package mindbadger.cah.webservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import mindbadger.cah.game.Game;
import mindbadger.cah.game.GameManager;

@RestController
public class GamesForTypeService {
	@Autowired
	GameManager gameManager;

	@RequestMapping(value="/games/getForType", method=RequestMethod.GET)
    public @ResponseBody List<Game> getGamesForType(@RequestParam(value="gameType") String gameType) {
		return gameManager.getGamesForType(gameType);
    }
	
	@RequestMapping(value="/game/addPlayerToExistingGame", method=RequestMethod.POST)
    public void addPlayerToExistingGame(@RequestParam(value="gameId") String gameId, @RequestParam(value="player") String player) {
		Game game = gameManager.getGames().get(gameId);
		game.addPlayerToGame(player);
    }

	@RequestMapping(value="/game/addPlayerToNewGame", method=RequestMethod.POST)
    public void addPlayerToNewGame(@RequestParam(value="gameType") String gameType, @RequestParam(value="player") String player) {
		gameManager.createNewGameAndAddFirstPlayer(gameType, player);
    }
}
