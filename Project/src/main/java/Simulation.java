import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
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

            if (time % Constant.GRAIN_GROWTH_INTERVAL == 0) {
                for (Patch patch : board.getPatches()) {
                    patch.addGrain(Constant.GRAIN_GROWTH_AMOUNT);
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
            int divided = patch.removeGrain() / people.size();
            for (Person person : people) {
                person.addGrain(divided);
            }
        }
    }

    // TODO have a function to export data to CSV

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
        for (int i = 0; i < args.num_people; i++) {
            Person person = Person.makeRandom(args, random, board, 0);
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
    	Arguments arguments = new Arguments(args);
        Simulation simulation = new Simulation();       
        simulation.setup(arguments);
//        System.out.println(simulation.board.patchesToString());

        simulation.run(arguments.time_max);

        simulation.csv.closeFile();
        // ...
    }
}
