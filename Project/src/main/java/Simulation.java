import java.util.List;

/**
 * Created by Jack on 3/5/2017.
 */
public class Simulation {

    boolean running;
    List<Patch> patches;
    List<Person> people;

    void run() {
        while (running) {
            for (Person person : people) {
                person.tick();
            }

            for (Patch patch : patches) {
                patch.tick();
            }
        }
    }

    void setup() {
        // TODO
    }
}
