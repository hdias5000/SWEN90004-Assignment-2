
public class Arguments {
	
	public int time_max;
	
	//the total population on the board
	public int num_people;
	
	//the maximum value of people can see the farthest
	public int max_vision;
	
	public int metabolism_max;
	
	// the minimum value of life expectancy
	public int life_expentancy_min;
	
	//the maximum value of life expectancy
	public int life_expentancy_max;
	
	public double percent_best_land;
	
	//the amount of time it takes to grow a grain
	public double grain_growth_interval;
	
	//the amount of grain which can plant on a patch a tick 
	public int grain_growth_rate;
	
	public String file_name;
	
	public Arguments (String[] args){
		num_people = Integer.parseInt(args[0]);
		max_vision = Integer.parseInt(args[1]);
		metabolism_max = Integer.parseInt(args[2]);
		life_expentancy_min = Integer.parseInt(args[3]);
		life_expentancy_max = Integer.parseInt(args[4]);
		percent_best_land = Double.parseDouble(args[5]);
		grain_growth_interval = Double.parseDouble(args[6]);
		grain_growth_rate = Integer.parseInt(args[7]);
		time_max = Integer.parseInt(args[8]);
		file_name = args[9];
	}

}
