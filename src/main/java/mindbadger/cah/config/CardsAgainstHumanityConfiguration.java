package mindbadger.cah.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import mindbadger.cah.card.Card;
import mindbadger.cah.card.CardType;

@Configuration
public class CardsAgainstHumanityConfiguration {

    @Bean
    @Qualifier("allBlackCards")
    public List<Card> allBlackCards () {
    	return readCardsFromFile("blackcards.txt", CardType.BLACK);
    }

    @Bean
    @Qualifier("allWhiteCards")
    public List<Card> allWhiteCards () {
    	return readCardsFromFile("whitecards.txt", CardType.WHITE);
    }

	private List<Card> readCardsFromFile(String filename, CardType cardType) {
		List<Card> cards = new ArrayList<Card> ();
		
		Resource resource = new ClassPathResource(filename);

		BufferedReader reader = null;
		try {
			InputStream resourceInputStream = resource.getInputStream();
			reader = new BufferedReader(new InputStreamReader(resourceInputStream));
			
			String line;
			 while ((line = reader.readLine()) != null) {
				 cards.add(new Card (cardType, line));
			} 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
	        try {
	            reader.close();
	        } catch (IOException e) {
	        	e.printStackTrace();
	        }
	    }
		
		return cards;
	}
	
}
