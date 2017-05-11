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
	
	// File writer
	private PrintWriter pw;
	
	// 2 decimal places formatting
	DecimalFormat df = new DecimalFormat("####0.00");
	
	/**
	 *  Open or create a csv file and have titles ready
	 * @throws FileNotFoundException
	 */
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
	}
	
	/**
	 * Count people in different wealth level
	 * @param wealth A list of wealth of all people
	 */
	public void countPeople(int[] wealth) {
		
		// the wealth of the richest person
		double max_wealth = (double) wealth[wealth.length-1];
		
		int num_low = 0;
		int num_mid = 0;
		int num_high = 0;
		
		// low:  wealth <= max_wealth/3,
		// mid:  max_wealth/3 < wealth <= 2*max_wealth/3
		// high: wealth > 2*max_wealth/3
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
	
	/**
	 * Total number of wealth of people not richer than nth poorest people
	 * @param wealth A list of wealth of each person
	 * @param n nth poorest people
	 * @return total wealth not richer than nth people
	 */
	public double totalWealth(int[] wealth, int n){

		double sum = 0.0;
		for(int i = 0; i <= n; i++){
			sum += wealth[i];
		}
		return sum;
	}
	
	/**
	 * Calculate Gini Coefficient
	 * Formula refers to: https://en.wikipedia.org/wiki/Gini_coefficient#Calculation
	 * @param wealth A list of wealth of each person
	 */
	public void calculateGini(int[] wealth){
		
		StringBuilder sb = new StringBuilder();
		
		double gini;
		double sum_wealth = totalWealth(wealth, wealth.length-1);
		double sum_rank_wealth = 0.0;
		for (int i = 0; i < wealth.length; i++) {
			sum_rank_wealth += wealth[i] * (i+1);
		}
		gini = (2*sum_rank_wealth)/(wealth.length*sum_wealth)
				-((wealth.length+1)/wealth.length);
		
		sb.append(gini+",");
		pw.write(sb.toString());
	}
	
	/**
	 * Generate the Lorenz Curve, represent as a list of coordinates
	 * Number of points equals to total number of people
	 * @param wealth
	 */
	public void generateLorenz(int[] wealth) {
		
		StringBuilder sb = new StringBuilder();
		
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
	
	/**
	 * Generate the list of wealth of each person from the current board
	 * @param board The board which holds all people
	 * @return A list of wealth of each person
	 */
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
	
	/**
	 * Record information at a given time tick
	 * @param board The current board
	 * @param time Current time tick
	 */
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
		
		// calculate gini index
		calculateGini(wealth);
		
		// output Lorenz curve
		generateLorenz(wealth);
	}
	
	/**
	 * Close the file when simulation stops
	 */
	public void closeFile(){
		pw.close();
	}
}
