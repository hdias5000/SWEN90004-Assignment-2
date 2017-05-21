import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Jack on 3/5/2017.
 */
public class Board {
	private Arguments args;
    private int width;
    private int height;

    private Map<Person, Point> positions;
    private Patch[][] patches;

    /**
     * Perform diffuse operation in Netlogo
     *
     * @param values     matrix of values to apply diffuse
     * @param height     height of matrix
     * @param width      width of matrix
     * @param x          x coordinate of point of diffuse
     * @param y          y coordinate of point of diffuse
     * @param percentage percentage of point to be removed and shared for diffuse
     */
    private static void diffuse(double[][] values, int height, int width, int x, int y, double percentage) {
        double divided = values[x][y] * percentage / 8.0;
        values[x][y] -= values[x][y] * percentage;

        // wraps around values out of range
        int x_prev = x - 1 < 0 ? width - 1 : x - 1;
        int x_next = x + 1 >= width ? 0 : x + 1;
        int y_prev = y - 1 < 0 ? height - 1 : y - 1;
        int y_next = y + 1 >= height ? 0 : y + 1;

        // added divided shared values to nearby cells
        values[x_prev][y_prev] += divided;
        values[x][y_prev] += divided;
        values[x_next][y_prev] += divided;
        values[x_prev][y] += divided;
        values[x_next][y] += divided;
        values[x_prev][y_next] += divided;
        values[x][y_next] += divided;
        values[x_next][y_next] += divided;
    }

    /**
     * Initialise a new board with people with random grain of patches
     * and random position of people
     *
     * @param width  width of board
     * @param height height of board
     */
    public Board(Arguments args, int width, int height) {
    	this.args = args;
        this.width = width;
        this.height = height;
        this.positions = new HashMap<>();
        patches = new Patch[height][width];

        Random random = new Random();
        // determine patch initial values
        double[][] maxGrainVals = new double[height][width];
        double[][] initialGrainVals = new double[height][width];

        // give some patches the highest amount of grain possible --
        // these patches are the "best land"
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (random.nextFloat() < args.percentBestLand) {
                    maxGrainVals[i][j] = Constant.MAX_GRAIN;
                    initialGrainVals[i][j] = maxGrainVals[i][j];
                } else {
                    maxGrainVals[i][j] = 0;
                    initialGrainVals[i][j] = 0;
                }
            }
        }

        // spread that grain around the window a little and put a little back
        // into the patches that are the "best land" found above
        for (int n = 0; n < 5; n++) {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (maxGrainVals[i][j] != 0) {
                        initialGrainVals[i][j] = maxGrainVals[i][j];
                        diffuse(initialGrainVals, height, width, i, j, 0.25);
                    }
                }
            }
        }

        // spread the grain around some more
        for (int n = 0; n < 10; n++) {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    diffuse(initialGrainVals, height, width, i, j, 0.25);
                }
            }
        }

        // initialise patches
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int grainHere = (int) initialGrainVals[i][j];
                // initial grain level is also maximum
                patches[i][j] = new Patch(grainHere, grainHere);
            }
        }
    }

    /**
     * Get all people on the board (people alive)
     *
     * @return set of people on the board
     */
    public Set<Person> getPeople() {
        return positions.keySet();
    }

    /**
     * Get all patches on the board
     *
     * @return set of patches on the board
     */
    public Set<Patch> getPatches() {
        // TODO more efficient implementation
        Set<Patch> s = new HashSet<>();
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
     *
     * @param p person to be moved
     * @param x x coordinate on board
     * @param y y coordinate on board
     */
    public void move(Person p, int x, int y) {
        positions.remove(p);
        positions.put(p, new Point(x, y));
    }

    public Point getPosition(Person p) {
        return positions.get(p);
    }

    /**
     * Get positions for all people on board
     * @return
     */
    public Map<Point, Set<Person>> getAllPositions() {
        Map<Point, Set<Person>> posMap = new HashMap<Point, Set<Person>>();
        for (Map.Entry<Person, Point> entry : positions.entrySet()) {
            if (!posMap.containsKey(entry.getValue())) {
                posMap.put(entry.getValue(), new HashSet<Person>());
            }
            posMap.get(entry.getValue()).add(entry.getKey());
        }
        return posMap;
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
    public Patch getPatchAt(int x, int y) {
        return patches[x][y];
    }

    /**
     * Get a string representation of patches
     * @return string representation of patches
     */
    public String patchesToString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < height; i++) {
            Patch[] row = patches[i];
            List<String> strings = Arrays.stream(row).map(
                    p -> String.format("%6s", p.toString())
            ).collect(Collectors.toList());
            sb.append(String.join(" ", strings));
            sb.append("\n");
        }
        return sb.toString();
    }
}
