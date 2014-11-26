package pairs;


public class PairInstance implements IPair {

	private String[] destination;
	
	public PairInstance(String[] array){
		this.destination = array;
	}
	
	@Override
	public boolean pair(String first, String second) {
		String source = first+second;
		boolean rtn = destination[0].equals(source) || destination[1].equals(source);
		//if(rtn)
		//	System.out.println(first + "->" + second);
		return rtn;
	}

}
