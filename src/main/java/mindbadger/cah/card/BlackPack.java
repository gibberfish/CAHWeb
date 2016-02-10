package mindbadger.cah.card;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class BlackPack extends AbstractPack {
	public static final List<BlackCard> allBlackCards = new ArrayList<BlackCard> ();

	@Override
	protected void initialisePack() {
		readCardsFromFile("blackcards.txt");		
		pack = new ArrayList<Card>(allBlackCards);
	}
	
	//TODO Duplicated in the WhitePack - needs refactoring
	private void readCardsFromFile(String filename) {
		Resource resource = new ClassPathResource(filename);

		BufferedReader reader = null;
		try {
			InputStream resourceInputStream = resource.getInputStream();
			reader = new BufferedReader(new InputStreamReader(resourceInputStream));
			
			String line;
			 while ((line = reader.readLine()) != null) {
				allBlackCards.add(new BlackCard (line));
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
	}
}
