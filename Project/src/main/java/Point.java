/**
 * Point in 2D space
 * Created by Jack on 3/5/2017.
 */
public class Point {
    private int x;
    private int y;

    /**
     * Get x value of the point
     * @return x value of the point
     */
    public int getX() {
        return x;
    }

    /**
     * Get y value of the point
     * @return y value of the point
     */
    public int getY() {
        return y;
    }

    /**
     * Construct a new point
     *
     * @param x x value
     * @param y y value
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
