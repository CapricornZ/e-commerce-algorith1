package process;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import e_commerce.algorithm1.App;
import e_commerce.algorithm1.FileOutput;

public class Processor3O implements IProcessor {

	private static final Logger logger = LoggerFactory.getLogger(Processor3O.class);
	
	private int offset;
	private boolean[] source;
	public Processor3O(boolean[] source, int offset){
		this.offset = offset;
		this.source = source;
	}
	
	@Override
	public void execute() {
		
		Constructor<ICycle> constructor = null;
		try {
			@SuppressWarnings("unchecked")
			Class<ICycle> Cycle = (Class<ICycle>) Class.forName(App.Class3O);
			constructor = Cycle.getConstructor(int.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int cycleStep = App.cycleStep;
		logger.debug("3O FOUND");
		FileOutput.write("3O FOUND");
		int totalSum = 0;
		List<Integer> steps = new ArrayList<Integer>();
		for(int i=0; i+offset<this.source.length; i+=cycleStep){
			ICycle cycle = null;
			if(steps.size() == 0)
				try {
					cycle = (ICycle) constructor.newInstance(1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			else{
				int step = 2;
				for(int var : steps)
					step += Math.abs(var);
				try {
					cycle = (ICycle) constructor.newInstance(step);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			int length = this.offset+i+(cycleStep-1)<=this.source.length ? cycleStep : this.source.length-(this.offset+i);
			cycle.execute(this.source, this.offset+i, length);
			int sum = cycle.getSum();
			steps.add(sum);
			
			boolean bStop = false;
			for(int index=0; !bStop && index<cycle.getProcess().size(); index++){
				int val = cycle.getProcess().get(index);
				totalSum += val;
				//System.out.print(String.format("%s%d", val<0?"":"+", val));
				logger.debug(String.format("%s%d", val<0?"":"+", val));
				FileOutput.write(String.format("%s%d", val<0?"":"+", val));
				if(totalSum >= 2)
					bStop = true;
			}
			if(bStop)
				break;
			if(i==0 && sum == 0)
				break;
		}

		logger.debug(String.format("=%d\r\n", totalSum));
		FileOutput.write(String.format("=%d\r\n", totalSum));
	}

}
