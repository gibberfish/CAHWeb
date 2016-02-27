package mindbadger.cah;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mindbadger.cah.game.GameType;

@Configuration
public class GameTypeConfiguration {
    @Bean
    public GameType cardsAgainstHumanityGameType () {
        return new GameType ("cardsAgainstHumanity", "Cards Against Humanity", "cah.html");
    }
    
    @Bean
    public GameType fluxxGameType () {
        return new GameType ("fluxx", "Fluxx", "fluxx.html");
    }    
}
