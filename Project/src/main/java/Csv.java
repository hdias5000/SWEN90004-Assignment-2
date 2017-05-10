import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;

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
	private int[] wealth;
	private Point[] lorenz;
	
	DecimalFormat df = new DecimalFormat("####0.00");
	
	
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
		//pw.close();
	}
	
	public void calculateGini(){
		
	}
	
	/**
	 * Total number of people, total number of wealth
	 * sort people from low wealth to high
	 * x = wealth of yth poorest people/ total wealth holding by people poorer than that people
	 * y = yth poorest people/ total number of people
	 */
	public void generateLorenze(Board board){
		wealth = null;
		wealth = new int[Constant.NUM_PEOPLE];
		int i = 0;
		for (Person p : board.getPeople()) {
			wealth[i] = p.getGrain();
			i += 1;
		}
		Arrays.sort(wealth);
		
		StringBuilder sb = new StringBuilder();
		lorenz = null;
		lorenz = new Point[Constant.NUM_PEOPLE];
		for (int u = 0; u < Constant.NUM_PEOPLE; u++) {
			double ud = (double) u;
			double x = ((ud+1) / Constant.NUM_PEOPLE)*100;
			double y;
			y = (totalWealth(wealth, u) / totalWealth(wealth, Constant.NUM_PEOPLE-1))*100;
			sb.append(df.format(x)+"% ; "+df.format(y)+"%");
			if (u == Constant.NUM_PEOPLE - 1){
				sb.append("\n");
			}
			else {
				sb.append(",");
			}
		}
		pw.write(sb.toString());
		
	}
	
	public double totalWealth(int[] wealth, int n){

		double sum = 0.0;
		for(int i = 0; i <= n; i++){
			sum += wealth[i];
		}
		return sum;
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
