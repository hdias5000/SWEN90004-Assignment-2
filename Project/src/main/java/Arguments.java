/**
 * Class storing command line arguments
 */
public class Arguments {
    // the length of time for the simulation
    public int timeMax;

    // the total population on the board
    public int numPeople;

    // the maximum value of people can see the farthest
    public int maxVision;

    // the maximum metabolism a person can have
    public int metabolismMax;

    // the minimum value of life expectancy
    public int lifeExpectancyMin;

    // the maximum value of life expectancy
    public int lifeExpectancyMax;

    // the percentage of best land
    public double percentBestLand;

    // the amount of time it takes to grow a grain
    public double grainGrowthInterval;

    // the amount of grain which can plant on a patch a tick
    public int grainGrowthRate;

    // the filename of csv output
    public String filename;

    /**
     * Parse command line arguments
     *
     * @param args
     */
    public Arguments(String[] args) {
        numPeople = Integer.parseInt(args[0]);
        maxVision = Integer.parseInt(args[1]);
        metabolismMax = Integer.parseInt(args[2]);
        lifeExpectancyMin = Integer.parseInt(args[3]);
        lifeExpectancyMax = Integer.parseInt(args[4]);
        percentBestLand = Double.parseDouble(args[5]);
        grainGrowthInterval = Double.parseDouble(args[6]);
        grainGrowthRate = Integer.parseInt(args[7]);
        timeMax = Integer.parseInt(args[8]);
        filename = args[9];
    }

}
