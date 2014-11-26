package pairgenerator;

import pairs.IPair;
import pairs.PairCombo;

public class PairComboGenerator implements IPairGenerator {
	
	private PairArrayGenerator generator = new PairArrayGenerator();

	@Override
	public IPair generate(String source) {
		
		String[] array = source.split("\\|");
		IPair[] pairs = new IPair[array.length];
		for(int i=0; i<array.length; i++)
			pairs[i] = this.generator.generate(array[i]);
		return new PairCombo(pairs);
	}

}
