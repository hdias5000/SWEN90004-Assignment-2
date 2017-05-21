import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Main class for the simulation, runs from command line
 * Created by Jack on 3/5/2017.
 */
public class Simulation {
	Arguments args;
    Board board;
    Csv csv;
    int time = 0;

    /**
     * Run the simulation
     * @param length time length of simulation
     */
    void run(int length) {
        for (int i = 0; i < length; i++) {
            // TODO record data at this tick

            Set<Person> people = new HashSet<>();
            people.addAll(board.getPeople());
            for (Person person : people) {
                Point p = person.getNextPos();
                harvest();
                person.moveEatAgeDie(p);
            }

            if (time % args.grainGrowthInterval == 0) {
                for (Patch patch : board.getPatches()) {
                    patch.addGrain(args.grainGrowthRate);
                }
            }

            time += 1;
            
            csv.record(board, time);
        }
    }

    /**
     * Perform harvest activity, take grain from patches equally distribute to people at the patch
     */
    void harvest() {
        for (Map.Entry<Point, Set<Person>> entry: board.getAllPositions().entrySet()) {
            Point p = entry.getKey();
            Set<Person> people = entry.getValue();
            Patch patch = board.getPatchAt(p.getX(), p.getY());
            // NOTE integer division below
            int divided = patch.removeAll() / people.size();
            for (Person person : people) {
                person.addGrain(divided);
            }
        }
    }

    /**
     * Set up the simulation
     * @throws FileNotFoundException 
     */
    void setup(Arguments arguments) throws FileNotFoundException {
    	args = arguments;
        board = new Board(args, Constant.BOARD_WIDTH, Constant.BOARD_HEIGHT);
        csv = new Csv(args);

        // put people to random positions
        Random random = new Random();
        for (int i = 0; i < args.numPeople; i++) {
            Person person = Person.makeRandom(args, random, board);
            int x = random.nextInt(Constant.BOARD_WIDTH);
            int y = random.nextInt(Constant.BOARD_HEIGHT);
            board.put(person, x, y);
        }
        csv.record(board, time);
    }

    /**
     * The main function
     * @param args command line arguments
     * @throws FileNotFoundException 
     */
    public static void main(String[] args) throws FileNotFoundException {
        // parse arguments
    	Arguments arguments = new Arguments(args);

    	// setup simulation
        Simulation simulation = new Simulation();       
        simulation.setup(arguments);

        // run simulation
        simulation.run(arguments.timeMax);

        // save csv output
        simulation.csv.closeFile();
    }
}
