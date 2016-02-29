package mindbadger.gameserver.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import mindbadger.gameserver.player.Player;
import mindbadger.gameserver.player.PlayerState;

public class PlayerListSerializer extends JsonSerializer<List<Player>> {
    @Override
    public void serialize(final List<Player> players, final JsonGenerator generator, final SerializerProvider provider)
    	throws IOException, JsonProcessingException {
    	
        final List<SimplePlayer> simplePlayers = new ArrayList<SimplePlayer> ();
        for (final Player player : players) {
        	simplePlayers.add(new SimplePlayer(player.getName(), player.getPlayerState()));
        }
        generator.writeObject(simplePlayers);
    }
    
    static class SimplePlayer {
    	private String name;
    	private PlayerState playerState;
    	SimplePlayer (String name, PlayerState playerState) {
    		this.name = name;
    		this.playerState = playerState;
    	}
    	public String getName() {
    		return name;
    	}
    	public PlayerState getPlayerState () {
    		return playerState;
    	}
    }
}
