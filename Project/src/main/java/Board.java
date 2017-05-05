import java.util.*;

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
     * Get all people on the board (people alive)
     * @return set of people on the board
     */
    public Set<Person> getPeople() {
        return positions.keySet();
    }

    /**
     * Get all patches on the board
     * @return set of patches on the board
     */
    public Set<Patch> getPatches() {
        // TODO more efficient implementation
        Set<Patch> s = new HashSet<Patch>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                s.add(patches[i][j]);
            }
        }
        return s;
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
     * Move a person on board to a new position
     * @param p person to be moved
     * @param x x coordinate on board
     * @param y y coordinate on board
     */
    public void move(Person p, int x, int y) {
        // TODO
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
     * Get people at a position
     *
     * @param x x coordinate of board
     * @param y y coordinate of baord
     * @return all people at the position
     */
    public List<Person> getPeopleAt(int x, int y) {

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
