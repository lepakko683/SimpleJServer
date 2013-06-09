package le683.main;

import java.io.OutputStream;
import java.io.PrintStream;

public class AdvPrtStream extends PrintStream {

	public AdvPrtStream(OutputStream out) {
		super(out);
	}
	
	public void flush(){
		super.flush();
		this.print("S: ");
	}

}
