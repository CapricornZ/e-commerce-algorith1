package process;

public class Start{

	static public IProcessor findProcessor(boolean[] source) {
		
		IProcessor processor = null;
		boolean bFound = false;
		for(int offset=2; !bFound && offset<source.length; offset++){
			
			if(offset>1 && source[offset] && source[offset-1] && source[offset-2])//3*o
				processor = new Processor3O(source, offset+1);
			
			if(offset>1 && !source[offset] && !source[offset-1] && !source[offset-2])//3*x
				processor = new Processor3X(source, offset+1);
			
			bFound = processor != null;
		}
		
		return processor;
	}

}
