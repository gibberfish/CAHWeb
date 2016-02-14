package mindbadger.cah.webservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import mindbadger.cah.game.GameManager;
import mindbadger.cah.game.GameType;

@Controller
@RequestMapping("/game/type/getAll")
public class GameTypeService {
	@Autowired
	GameManager gameManager;

	@RequestMapping(method=RequestMethod.GET)
    public @ResponseBody List<GameType> getGameTypes() {
        return gameManager.getGameTypes();
    }
}
