import java.util.Random;

/**
 * Person in the simulation
 * Created by Jack on 3/5/2017.
 */
public class Person {
    private Arguments args;
    private Board board;
    private int grain;
    private int metabolism;
    private int age;
    private int maxAge;
    private int vision;

    /**
     * Construct a new person
     *
     * @param args       command line arguments
     * @param board      board the person is on
     * @param grain      amount of initial grain(wealth) of the person
     * @param metabolism metabolism of the person
     * @param maxAge     number of ticks the person can live in maximum
     */
    public Person(Arguments args, Board board, int grain, int metabolism, int maxAge) {
        this.args = args;
        this.board = board;
        this.grain = grain;
        this.metabolism = metabolism;
        // random age between 0 and max age
        this.age = new Random().nextInt(maxAge);
        this.maxAge = maxAge;
        // random vision between 0 and max vision
        this.vision = 1 + new Random().nextInt(args.maxVision);
    }

    /**
     * Make a random person
     *
     * @param args   command line arguments
     * @param random random object to generate random numbers
     * @param board  board the person is on
     * @return person with random properties
     */
    static Person makeRandom(Arguments args, Random random, Board board) {
        int metabolism = 1 + random.nextInt(args.metabolismMax);
        int wealth = metabolism + random.nextInt(50);
        int lifeExpectancy = args.lifeExpectancyMin
                + random.nextInt(args.lifeExpectancyMax + 1);
        Person person = new Person(args, board, wealth, metabolism, lifeExpectancy);
        return person;
    }

    /**
     * Get the amount of grain the person currently holds
     *
     * @return amount of grain
     */
    public int getGrain() {
        return grain;
    }

    /**
     * Add to the amount of grain the person holds
     *
     * @param amount amount to be added
     */
    public void addGrain(int amount) {
        grain += amount;
    }

    /**
     * Find the next point to move to
     *
     * @return next point to move to
     */
    Point getNextPos() {
        Point at = board.getPosition(this);
        Patch patch;
        int compareGrain;

        patch = board.getPatchAt(at.getX(), at.getY());

        int x_prev = at.getX() - vision < 0 ? (at.getX() - vision + 1) + 50 : at.getX() - vision;
        int x_next = at.getX() + vision >= 50 ? (at.getX() + vision) - 50 : at.getX() + vision;
        int y_prev = at.getY() - vision < 0 ? (at.getY() - vision + 1) + 50 : at.getY() - vision;
        int y_next = at.getY() + vision >= 50 ? (at.getY() + vision) - 50 : at.getY() + vision;

        compareGrain = patch.getGrain();

        for (int i = x_prev; i != x_next + 1; i++) {
            i = i >= 50 ? 0 : i;
            for (int j = y_prev; j != y_next + 1; j++) {
                j = j >= 50 ? 0 : j;

                patch = board.getPatchAt(i, j);

                if (compareGrain < patch.getGrain()) {
                    compareGrain = patch.getGrain();
                    at = new Point(i, j);
                }
            }
        }
        return at;
    }

    /**
     * Move, eat, age and die (if out of grain)
     */
    void moveEatAgeDie(Point to) {
        // move
        board.move(this, to.getX(), to.getY());

        // eat
        grain -= metabolism;

        // age
        age += 1;

        // die (and produce offspring)
        if (age > maxAge || grain <= 0) {
            board.remove(this);
            board.put(makeRandom(args, new Random(), board), to.getX(), to.getY());
        }
    }
}
