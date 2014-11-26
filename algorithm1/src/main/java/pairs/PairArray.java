package pairs;

public class PairArray implements IPair {

	private IPair[] pairs;
	public PairArray(IPair[] pairs){
		this.pairs = pairs;
	}
	
	@Override
	public boolean pair(String first, String second) {
		
		boolean rtn = false;
		for(int i=0; i<pairs.length && !rtn; i++)
			rtn = pairs[i].pair(first, second);
		return rtn;
	}

}
