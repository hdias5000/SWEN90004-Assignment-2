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
    public Person(Arguments args, Board board, int grain, int metabolism, int maxAge, int age) {
        this.args = args;
        this.board = board;
        this.grain = grain;
        this.metabolism = metabolism;
        this.age = age;
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
        // random age between 0 and expectancy
        int age = random.nextInt(lifeExpectancy);
        return new Person(args, board, wealth, metabolism, lifeExpectancy, age);
    }

    String getWealthLevel(int maxWealth) {
        // low:  wealth <= max_wealth/3,
        // mid:  max_wealth/3 < wealth <= 2*max_wealth/3
        // high: wealth > 2*max_wealth/3
        if (grain <= maxWealth / 3) {
            return "low";
        } else if (grain> maxWealth / 3 && grain <= maxWealth * 2 / 3) {
            return "mid";
        } else {
            return "high";
        }
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
        if (Constant.PROPORTIONAL_METABOLISM_ENABLED) {
            int consumed = (int) Math.min(Constant.METABOLISM_MIN, (int)grain * Constant.METABOLISM_PROPORTION);
            grain -= consumed;
        } else {
            grain -= metabolism;
        }

        // age
        age += 1;

        // die (and produce offspring)
        if (age > maxAge || grain <= 0) {
            Person offspring = makeRandom(args, new Random(), board);
            if (Constant.WEALTH_INHERITANCE_ENABLED) {
                // if died from aging, offspring get a percentage of wealth
                if (grain > 0) {
                    offspring.grain = (int) (this.grain * Constant.WEALTH_INHERITANCE);
                }
            }
            board.remove(this);

            offspring.age = 0;
            board.put(offspring, to.getX(), to.getY());
        }
    }
}
