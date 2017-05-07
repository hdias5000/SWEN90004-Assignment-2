import java.util.List;
import java.util.Random;

/**
 * Created by Jack on 3/5/2017.
 */
public class Simulation {

    boolean running;
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
                person.tick();
            }

            if (time % Constant.GRAIN_GROWTH_INTERVAL == 0) {
                for (Patch patch : board.getPatches()) {
                    patch.addGrain(Constant.GRAIN_GROWTH_AMOUNT);
                }
            }

            time += 1;
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
