import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Created by Jack on 3/5/2017.
 */
public class Simulation {
    Board board;
    int time = 0;

    /**
     * Run the simulation
     * @param length time length of simulation
     */
    void run(int length) {
        for (int i = 0; i < length; i++) {
            // TODO record data at this tick

            for (Person person : board.getPeople()) {
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
     */
    void setup() {
        board = new Board(Constant.BOARD_WIDTH, Constant.BOARD_HEIGHT);

        // put people to random positions
        Random random = new Random();
        for (int i = 0; i < Constant.NUM_PEOPLE; i++) {
            int metabolism = 1 + random.nextInt(Constant.METABOLISM_MAX);
            int wealth = metabolism + random.nextInt(50);
            int lifeExpectancy = Constant.LIFE_EXPECTANCY_MIN
                    + random.nextInt(Constant.LIFE_EXPECTANCY_MAX + 1);
            Person person = new Person(board, wealth, metabolism, lifeExpectancy);
            int x = random.nextInt(Constant.BOARD_WIDTH);
            int y = random.nextInt(Constant.BOARD_HEIGHT);
            board.put(person, x, y);
        }
    }

    /**
     * The main function
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Simulation simulation = new Simulation();
        simulation.setup();
        // ...
    }
}
