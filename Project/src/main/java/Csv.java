import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * This class is for output formatting
 * @author Chao Li
 * @date 9/5/2017
 */
public class Csv {
	
	// Name of the output file
	public static final String FILE_NAME = "output.csv";
	
	private PrintWriter pw;
	
	private int time;
	private int num_low;
	private int num_mid;
	private int num_high;
	
	
	public Csv() throws FileNotFoundException {
		pw = new PrintWriter(new File(FILE_NAME));
		StringBuilder sb = new StringBuilder();
		sb.append("Time tick,");
		sb.append("Number of people with low wealth,");
		sb.append("Number of people with mid wealth,");
		sb.append("Number of people with high wealth,");
		sb.append("Lorenze curve,");
		sb.append("Gini index,");
		sb.append("\n");
		pw.write(sb.toString());
		// for testing usage
		pw.close();
	}
	
	public void calculateGini(){
		
	}
	
	public void generateLorenze(){
		
	}
	
	public void writeLine(){
		
	}
	
	/**
	 * Close the file when simulation stops
	 */
	public void closeFile(){
		pw.close();
	}
}
