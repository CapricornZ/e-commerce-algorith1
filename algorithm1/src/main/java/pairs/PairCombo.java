package pairs;


public class PairCombo implements IPair {
	
	private IPair[] pairs;
	public PairCombo(IPair[] pairs){
		this.pairs = pairs;
	}

	@Override
	public boolean pair(String first, String second) {
		int index = first.length()-1;
		return pairs[index].pair(first, second);
	}
}