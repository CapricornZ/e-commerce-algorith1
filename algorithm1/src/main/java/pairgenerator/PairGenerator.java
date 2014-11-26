package pairgenerator;

import pairs.IPair;
import pairs.PairInstance;

public class PairGenerator implements IPairGenerator {

	public IPair generate(String source) {
		
		String[] array = source.split(",");
		return new PairInstance(new String[]{array[0]+array[1], array[1]+array[0]});
	}
}
