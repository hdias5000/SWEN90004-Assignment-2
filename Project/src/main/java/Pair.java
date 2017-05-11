/**
 * Point in 2D space
 * Created by Chao on 10/5/2017.
 */
public class Pair {
    private double x;
    private double y;

    /**
     *
     * @return
     */
    public double getX() {
        return x;
    }

    /**
     *
     * @return
     */
    public double getY() {
        return y;
    }

    /**
     * Construct a new point
     *
     * @param x x coordinate
     * @param y y coordinate
     */
    public Pair(double x, double y) {
        this.x = x;
        this.y = y;
    }
}