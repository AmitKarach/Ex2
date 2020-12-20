package api;

import java.lang.Math;

/**
 * this will help us reprisent evreything in the game
 */
public class GLocation implements geo_location {
    private final double x;
    private final double y;
    private final double z;

    public GLocation(double newx, double newy, double newz) {
        x = newx;
        y = newy;
        z = newz;
    }

    public GLocation(geo_location temp) // Needed Constructor
    {
        x = temp.x();
        y = temp.y();
        z = temp.z();
    }

    public GLocation(double x, double y) {
        this(x, y, 0);
    }

    @Override
    public String toString() {
        return "GLocation{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    /**
     * @return the x
     */
    @Override
    public double x() {
        return x;
    }

    /**
     * @return the y
     */
    @Override
    public double y() {
        return y;
    }

    /**
     * @return the z
     */
    @Override
    public double z() {
        return z;
    }

    /**
     * checks the distance between two points
     * @param g-the other point
     * @return the distance
     */
    @Override
    public double distance(geo_location g) {
        return Math.sqrt(Math.pow(this.x - g.x(), 2) + Math.pow(this.y - g.y(), 2) + Math.pow(this.z - g.z(), 2));
    }
}
