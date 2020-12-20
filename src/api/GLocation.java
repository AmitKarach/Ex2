package api;

import java.lang.Math;

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

    @Override
    public double x() {
        return x;
    }

    @Override
    public double y() {
        return y;
    }

    @Override
    public double z() {
        return z;
    }

    @Override
    public double distance(geo_location g) {
        return Math.sqrt(Math.pow(this.x - g.x(), 2) + Math.pow(this.y - g.y(), 2) + Math.pow(this.z - g.z(), 2));
    }
}
