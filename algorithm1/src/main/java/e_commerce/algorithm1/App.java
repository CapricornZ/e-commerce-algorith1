package e_commerce.algorithm1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import e_commerce.algorithm1.App;
import pairgenerator.PairComboGenerator;
import pairs.IPair;
import process.IProcessor;
import process.Start;

public class App {
	
	public static int cycleStep = 3;
	public static String Class3X = "process.CycleNegtive";
	public static String Class3O = "process.CyclePositive";
	private static final Logger logger = LoggerFactory.getLogger(App.class);
	
	private static IPair pairEngine;
	private static int[] sourceStep3 = new int[] { 1, 2, 3, 5, 8, 13, 21, 34,
			55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765, 10946,
			17711, 28657, 46368 };

	public static void main(String[] args) throws IOException {

		String[] x = new String[] { "AAAA", "ABAB", "ABBA", "AABB", "BBBB", "BABA", "BAAB", "BBAA" };
		String[] y = new String[] { "AAAB", "AABA", "ABAA", "BAAA", "BBBA", "BBAB", "BABB", "ABBB" };

		String rule = "A,A;B,B|AA,BB;AB,BA|AAA,ABA;AAA,BBB;AAA,BAB;ABA,BBB;ABA,BAB;BBB,BAB;AAB,BAA;AAB,ABB;AAB,BBA;BAA,ABB;BAA,BBA;ABB,BBA|";

		String xx = "";
		for (int i = 0; i < x.length - 1; i++)
			for (int j = i + 1; j < x.length; j++) {
				xx += x[i] + "," + x[j] + ";";
			}
		rule += xx;

		xx = "";
		for (int i = 0; i < y.length - 1; i++)
			for (int j = i + 1; j < y.length; j++) {
				xx += y[i] + "," + y[j] + ";";
			}
		xx = xx.substring(0, xx.length() - 1);
		rule += xx;

		pairEngine = new PairComboGenerator().generate(rule);

		String file = args[0];
		String output = args[1];
		String fileName = null;
		{
			String[] array = file.split("/");
			fileName = array[array.length-1];
			array = fileName.split("\\.");
			fileName = String.format("%s%s_step%d.txt", output, array[0], cycleStep);
		}
		FileOutput.init(fileName);
		InputStreamReader read = new InputStreamReader(
				new FileInputStream(file), "UTF-8");// 考虑到编码格式
		BufferedReader bufferedReader = new BufferedReader(read);
		String lineTxt = null;
		int[] addupX = new int[100];
		int[] addupO = new int[100];
		int seq_x_max = 0, seq_o_max = 0;
		while ((lineTxt = bufferedReader.readLine()) != null) {

			String strSource = lineTxt;
			char[] arrSource = strSource.toCharArray();

			int header = step1(arrSource);
			boolean[] result = step2(strSource, header);

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < header + 1; i++){
				logger.debug("-");
				sb.append("-");
			}
			FileOutput.write(sb.toString());
			
			sb = new StringBuilder();
			boolean last = result[0];
			int count = 1;
			int countTrue = 0, countFalse = 0;
			if(last){
				countTrue ++ ;
				logger.debug("o");
				sb.append("o");
			} else {
				countFalse ++ ;
				logger.debug("x");
				sb.append("x");
			}
			
			for(int i=1; i<result.length; i++){
				if (result[i]) {
					countTrue++;
					logger.debug("o");
					sb.append("o");
				} else {
					countFalse++;
					logger.debug("x");
					sb.append("x");
				}
				
				if(result[i] == last){
					count ++ ;
				} else {
					if(last){
						addupO[count-1]++;
						seq_o_max = seq_o_max>count?seq_o_max:count;
					}
					else{
						addupX[count-1]++;
						seq_x_max = seq_x_max>count?seq_x_max:count;
					}
					last = result[i];
					count = 1;
				}
			} FileOutput.write(sb.toString());

			logger.debug(" [ x:{} ({}%), o:{} ({}%) ]\r\n", 
					countFalse, ((float)countFalse*100/(float)(countFalse+countTrue)), 
					countTrue, ((float)countTrue*100/(float)(countFalse+countTrue)));
			logger.debug("{}\r\n", strSource);
			FileOutput.write(String.format(" [ x:%d (%f%%), o:%d (%f%%) ]\r\n",
					countFalse, ((float)countFalse*100/(float)(countFalse+countTrue)), 
					countTrue, ((float)countTrue*100/(float)(countFalse+countTrue))));
			FileOutput.write(String.format("%s\r\n", strSource));

			//step3(result, header);
			step3x(result);
		}
		
		int max = seq_x_max>seq_o_max?seq_x_max:seq_o_max;
		for(int i=0; i<max; i++){
			logger.debug("SEQ {} : {x:{}, o:{}}\r\n", i+1, addupX[i], addupO[i]);
		}
		read.close();
		FileOutput.close();
		
		/*boolean[] source = new boolean[]{false,false,false,false,false,false,false,true,true,false,true};
		step3x(source);*/
	}

	public static boolean pair(String first, String second) {
		return pairEngine.pair(first, second);
	}
	
	public static void step3x(boolean[] source){
		
		IProcessor processor = Start.findProcessor(source);
		if(null != processor){
			processor.execute();
		}
	}

	public static void step3(boolean[] source, int length) {
		int sum = 0;
		int indexSourceStep3 = 0;
		int max = 0;
		boolean foundMax = false;
		for (boolean e : source){
			if( max < sourceStep3[indexSourceStep3] && !foundMax){
				max = sourceStep3[indexSourceStep3];
			}
			
			if (e) {
				sum += sourceStep3[indexSourceStep3];
				logger.debug("+{}", sourceStep3[indexSourceStep3]);
				if (indexSourceStep3 != 0)
					indexSourceStep3 -= 1;
			} else {
				sum -= sourceStep3[indexSourceStep3];
				logger.debug("-{}", sourceStep3[indexSourceStep3]);
				indexSourceStep3 += 1;
			}
			
			if(sum >= 10)
				foundMax = true;
		}
		/*for (int indexSource = length + 1; indexSource < source.length; indexSource++) {
			if (source[indexSource]) {
				sum += sourceStep3[indexSourceStep3];
				logger.debug("+{}", sourceStep3[indexSourceStep3]);
				if (indexSourceStep3 != 0)
					indexSourceStep3 -= 1;
			} else {
				sum -= sourceStep3[indexSourceStep3];
				logger.debug("-{}", sourceStep3[indexSourceStep3]);
				indexSourceStep3 += 1;
			}
		}*/
		logger.debug(" = {} [ MAX: {} ]\r\n", sum, max);
	}

	public static boolean[] step2(String source, int length) {
		
		boolean[] rtn = new boolean[source.length()-length-1];
		//boolean[] rtn = new boolean[source.length()];
		int index = 1;
		while (true) {

			int i = 0;
			boolean isPair = false;

			for (; i < 4 && !isPair
					&& index + length + (i + 1) < source.length(); i++) {
				String first = source.substring(index, index + (i + 1));
				String second = source.substring(index + length, index + length
						+ (i + 1));
				isPair = pair(first, second);
				//rtn[index + length + i] = isPair;
				rtn[index -1 + i] = isPair;
			}
			if (i == 0)
				break;
			index += i;
		}

		return rtn;
	}

	public static int step1(char[] array) {

		int rtn = 1;
		int column = 0;
		int COLUMN = 4;
		char last = array[0]; // System.out.print(last);
		for (int index = 1; index < array.length && column < COLUMN; index++) {

			if (array[index] != last) {
				last = array[index];
				column++;
			}

			if (column <= COLUMN - 1)
				rtn++;
		}
		return rtn;
	}
}
