import java.util.List;

/**
 * Created by Jack on 3/5/2017.
 */
public class Simulation {

    boolean running;
    Board board;

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

            for (Patch patch : board.getPatches()) {
                patch.tick();
            }
        }
    }

    // TODO have a function to export data to CSV

    /**
     * Set up the simulation
     */
    void setup() {
        // TODO
    }

    /**
     * The main function
     * @param args command line arguments
     */
    public static void main(String[] args) {

    }
}
