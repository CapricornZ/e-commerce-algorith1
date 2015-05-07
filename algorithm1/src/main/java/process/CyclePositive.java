package process;

import java.util.ArrayList;
import java.util.List;

public class CyclePositive implements ICycle{
	
	private int step;
	public CyclePositive(int step){ this.step = step; }
	
	private int sum = 0;
	public int getSum(){ return this.sum; }
	
	private List<Integer> process = new ArrayList<Integer>();
	public List<Integer> getProcess(){ return this.process; }
	
	public void execute(boolean[] source, int offset, int length){
		
		this.sum = 0;
		int maxLen = offset+length < source.length ? offset+length : source.length;
		for(int i=0; offset+i<maxLen; i++)
			if(source[offset+i]){
				this.sum += this.step;
				this.process.add(this.step);
			} else {
				this.sum -= this.step;
				this.process.add(-this.step);
			}
	}
}