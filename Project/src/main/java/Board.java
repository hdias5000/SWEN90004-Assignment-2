import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Jack on 3/5/2017.
 */
public class Board {
    int width;
    int height;

    Map<Person, Point> positions;
    Patch[][] patches;

    /**
     * Initialise a new board with people with random grain of patches
     * and random position of people
     *
     * @param width  width of board
     * @param height height of board
     * @param people people to be put on board
     */
    public Board(int width, int height, List<Person> people) {
        this.width = width;
        this.height = height;
        this.positions = new HashMap<Person, Point>();
        patches = new Patch[height][width];

        // put people to random positions
        Random random = new Random();
        for (Person person : people) {
            put(person, random.nextInt(height), random.nextInt(width));
        }

        // initialise patches
        // TODO set random grain
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                patches[i][j] = new Patch();
            }
        }
    }

    /**
     * Put a person onto board
     *
     * @param p person to be added
     * @param x x coordinate on board
     * @param y y coordinate on board
     */
    public void put(Person p, int x, int y) {
        positions.put(p, new Point(x, y));
    }

    /**
     * Remove a person from board
     *
     * @param p person to be removed
     */
    public void remove(Person p) {
        positions.remove(p);
    }

    /**
     * Peek a patch on board
     *
     * @param x x coordinate on board
     * @param y y coordinate on board
     * @return the patch at position (x, y)
     */
    public Patch peek(int x, int y) {
        return patches[x][y];
    }
}
