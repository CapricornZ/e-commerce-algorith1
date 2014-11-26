package pairgenerator;

import pairs.IPair;
import pairs.PairArray;

public class PairArrayGenerator implements IPairGenerator {
	
	private PairGenerator generator = new PairGenerator();

	@Override
	public IPair generate(String source) {

		String[] array = source.split(";");
		IPair[] pairs = new IPair[array.length];
		for(int i=0; i<array.length; i++)
			pairs[i] = this.generator.generate(array[i]);
		return new PairArray(pairs);
	}

}
