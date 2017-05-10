import java.util.Random;

/**
 * Created by Jack on 3/5/2017.
 */
public class Person {
    Board board;
    int grain;
    int metabolism;
    int age;
    int maxAge;

    public Person(Board board, int grain, int metabolism, int maxAge) {
        this.board = board;
        this.grain = grain;
        this.metabolism = metabolism;
        // random age between 0 and max age
        this.age = new Random().nextInt(maxAge);
    }

    /**
     * Get the amount of grain the person currently holds
     * @return amount of grain
     */
    public int getGrain() {
        return grain;
    }

    /**
     *
     * @param amount
     */
    public void addGrain(int amount) {
        grain += amount;
    }

    /**
     * Get the age of the person
     * @return age of the person
     */
    public int getAge() {
        return age;
    }

    /**
     * Find the next point to move to
     * @return next point to move to
     */
    Point getNextPos() {
        // TODO ask Board about patches and decide where to go
        return null;
    }

    /**
     * Move, eat, age and die (if out of grain)
     */
    void moveEatAgeDie(Point to) {
        // move
        board.put(this, to.getX(), to.getY());

        // eat
        grain -= metabolism;

        // age
        age += 1;

        // die
        if (age > maxAge || grain <= 0) {
            board.remove(this);
        }
    }

//    /**
//     * Perform action for one tick
//     */
//    public void tick() {
//        Point p = getNextPos();
//        harvest();
//        moveEatAgeDie(p);
//    }
}