package e_commerce.algorithm1;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileOutput {
	
	static public FileOutputStream out;
	
	static public void init(String filepath) throws FileNotFoundException{
		out = new FileOutputStream(filepath);
	}
	
	static public void write(String content){
		try {
			out.write(content.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static public void close() throws IOException{
		out.close();
	}
}
