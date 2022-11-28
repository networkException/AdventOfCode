package de.nwex.applicationlib.math;

/**
 * Class to describe a point in a 3D coordinate system
 *
 * @author networkException
 *
 * @see #Coordinate(int, int, int)
 * @see #getDistanceTo(int, int, int)
 * @see #getDistanceTo(Coordinate)
 * @see #getX()
 * @see #getY()
 * @see #getZ()
 */
public class Coordinate
{
    /**
     * X value of the coordinate point
     */
    private int x;

    /**
     * Y value of the coordinate point
     */
    private int y;

    /**
     * Z value of the coordinate point
     */
    private int z;

    /**
     * The constructor which takes in the coordinates of the point
     *
     * @param x {@link #x}
     * @param y {@link #y}
     * @param z {@link #z}
     */
    public Coordinate(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Returns the distance between two points
     *
     * @param coordinate the other coordinate
     * @return the distance to the other coordinate as a double value
     */
    public double getDistanceTo(Coordinate coordinate)
    {
        return Math.sqrt((Math.pow(coordinate.getX() - getX(), 2)) + (Math.pow(coordinate.getY() - getY(), 2)) + (Math.pow(coordinate.getZ() - getZ(), 2)));
    }

    /**
     * Returns the distance between two points
     *
     * creates a new {@link Coordinate} and calls {@link #getDistanceTo(Coordinate)}
     *
     * @param x x of the other coordinate
     * @param y y of the other coordinate
     * @param z z of the other coordinate
     *
     * @return the distance to the other coordinate as a double value
     */
    public double getDistanceTo(int x, int y, int z)
    {
        return getDistanceTo(new Coordinate(x, y ,z));
    }

    /**
     * @return {@link #x}
     */
    public int getX()
    {
        return x;
    }

    /**
     * @return {@link #y}
     */
    public int getY()
    {
        return y;
    }

    /**
     * @return {@link #z}
     */
    public int getZ()
    {
        return z;
    }
}
