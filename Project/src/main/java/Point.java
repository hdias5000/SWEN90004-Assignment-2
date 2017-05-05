/**
 * Point in 2D space
 * Created by Jack on 3/5/2017.
 */
public class Point {
    int x;
    int y;

    /**
     *
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     * Construct a new point
     *
     * @param x x coordinate
     * @param y y coordinate
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
