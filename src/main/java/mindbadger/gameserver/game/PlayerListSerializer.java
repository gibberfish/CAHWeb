package mindbadger.gameserver.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import mindbadger.gameserver.player.Player;

public class PlayerListSerializer extends JsonSerializer<List<Player>> {
    @Override
    public void serialize(final List<Player> players, final JsonGenerator generator, final SerializerProvider provider)
    	throws IOException, JsonProcessingException {
    	
        final List<SimplePlayer> simplePlayers = new ArrayList<SimplePlayer> ();
        for (final Player player : players) {
        	simplePlayers.add(new SimplePlayer(player.getName()));                
        }
        generator.writeObject(simplePlayers);
    }
    
    static class SimplePlayer {
    	private String name;
    	SimplePlayer (String name) {
    		this.name = name;
    	}
    	public String getName() {
    		return name;
    	}
    }
}
