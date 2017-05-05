/**
 * Created by Jack on 3/5/2017.
 */
public class Person {

    Board board;
    float grain;
    float metabolism;
    int age;
    int maxAge;

    /**
     * Get the amount of grain the person currently holds
     * @return amount of grain
     */
    public float getGrain() {
        return grain;
    }

    /**
     * Get the age of the person
     * @return age of the person
     */
    public int getAge() {
        return age;
    }

    /**
     * Take grain from patch the person is currently at
     */
    void harvest() {
        // TODO
        // NOTE some job may need to be performed by board or simulation,
        // since multiple people at the same position share grain
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

    /**
     * Perform action for one tick
     */
    public void tick() {
        Point p = getNextPos();
        harvest();
        moveEatAgeDie(p);
    }
}
