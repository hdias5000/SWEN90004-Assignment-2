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
	
	DecimalFormat df = new DecimalFormat("####0.00");
	
	
	public Csv() throws FileNotFoundException {
		pw = new PrintWriter(new File(FILE_NAME));
		StringBuilder sb = new StringBuilder();
		sb.append("Time tick,");
		sb.append("Number of people with low wealth,");
		sb.append("Number of people with mid wealth,");
		sb.append("Number of people with high wealth,");
		sb.append("Gini Index,");
		sb.append("Lorenz Curve,");
		sb.append("\n");
		pw.write(sb.toString());
		// for testing usage
		//pw.close();
	}
	
	public void countPeople(int[] wealth) {
		double max_wealth = (double) wealth[wealth.length-1];
		int num_low = 0;
		int num_mid = 0;
		int num_high = 0;
		for(int i = 0; i < wealth.length; i++) {
			if (wealth[i] <= max_wealth/3) {
				num_low += 1;
			}
			else if (wealth[i] > max_wealth/3 && wealth[i] <= max_wealth*2/3) {
				num_mid += 1;
			}
			else {
				num_high += 1;
			}
		}
		StringBuilder sb = new StringBuilder();
		sb.append(num_low+","+num_mid+","+num_high+",");
		pw.write(sb.toString());
	}
	
	public double totalWealth(int[] wealth, int n){

		double sum = 0.0;
		for(int i = 0; i <= n; i++){
			sum += wealth[i];
		}
		return sum;
	}
	
	public void calculateGini(){
		
	}
	
	/**
	 * Total number of people, total number of wealth
	 * sort people from low wealth to high
	 * x = wealth of yth poorest people/ total wealth holding by people poorer than that people
	 * y = yth poorest people/ total number of people
	 */
	public Pair[] generateLorenz(int[] wealth) {
					
		Pair[] lorenz = new Pair[Constant.NUM_PEOPLE];
		
		for (int u = 0; u < Constant.NUM_PEOPLE; u++) {
			double ud = (double) u;
			double x = ((ud+1) / Constant.NUM_PEOPLE)*100;
			double y;
			y = (totalWealth(wealth, u) / totalWealth(wealth, Constant.NUM_PEOPLE-1))*100;
			lorenz[u] = new Pair(x,y);
		}
		return lorenz;
		
	}
	
	public void outputLorenz(Pair[] lorenz) {
		StringBuilder sb = new StringBuilder();
		for (int u = 0; u < lorenz.length; u++) {
			sb.append(df.format(lorenz[u].getX())+"% ; "+df.format(lorenz[u].getY())+"%");
			if (u == Constant.NUM_PEOPLE - 1){
				sb.append("\n");
			}
			else {
				sb.append(",");
			}
		}		
		pw.write(sb.toString());
	}
	
	public int[] generateWealthList(Board board){
		
		// initialize the array and get information from the board
		int[] wealth = new int[Constant.NUM_PEOPLE];
		int i = 0;
		for (Person p : board.getPeople()) {
			wealth[i] = p.getGrain();
			i += 1;
		}
		
		//sort the array in ascending order
		Arrays.sort(wealth);
		
		return wealth;
	}
	
	public void record(Board board, int time){
		
		// record time
		StringBuilder sb = new StringBuilder();
		sb.append(time+",");
		pw.write(sb.toString());
		
		// generate wealth array
		int[] wealth = new int[Constant.NUM_PEOPLE];
		wealth = generateWealthList(board);
		
		// count number of people in different level
		countPeople(wealth);		
		
		// calculate Lorenz curve
		Pair[] lorenz = generateLorenz(wealth);
		
		// calculate gini index
		
		// output Lorenz curve
		outputLorenz(lorenz);
	}
	
	/**
	 * Close the file when simulation stops
	 */
	public void closeFile(){
		pw.close();
	}
}
