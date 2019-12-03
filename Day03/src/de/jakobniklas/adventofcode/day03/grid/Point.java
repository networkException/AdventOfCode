package de.jakobniklas.adventofcode.day03.grid;

import java.util.Objects;

/**
 * Class storing a point in the grid
 *
 * @author networkException
 * @see #x
 * @see #y
 * @see #steps
 * @see #Point(int, int)
 * @see #getX()
 * @see #getY()
 * @see #getSteps()
 * @see #setSteps(int)
 * @see #addToSteps(int)
 * @see #equals(Object)
 * @see #hashCode()
 * @see #toString()
 */
public class Point
{
    /**
     * The x position in the grid, relative to the origin of every wire
     */
    private int x;

    /**
     * The y position in the grid, relative to the origin of every wire
     */
    private int y;

    /**
     * The amount of steps needed for a given point (this value is only used when finding an intersection. The current
     * step count is stored in {@link Wire#getSteps()})
     */
    private int steps;

    /**
     * Creates a new point
     *
     * @param x The relative x coordinate of the point
     * @param y The relative y coordinate of the point
     */
    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
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
     * @return {@link #steps}
     */
    public int getSteps()
    {
        return steps;
    }

    /**
     * @param steps {@link #steps}
     */
    public void setSteps(int steps)
    {
        this.steps = steps;
    }

    /**
     * Adds a given amount of steps to the point's steps
     *
     * @param steps {@link #steps}
     */
    public void addToSteps(int steps)
    {
        this.steps += steps;
    }

    /**
     * Checks if two points are equal (ignoring the step count)
     *
     * @param o The object to compare this to
     *
     * @return If this object is equal to a given object
     */
    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x &&
            y == point.y;
    }

    /**
     * @return A hash code of this object (ignoring the step count)
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(x, y);
    }

    /**
     * @return A string interpretation of the object
     */
    @Override
    public String toString()
    {
        return "Point{" +
            "x=" + x +
            ", y=" + y +
            ", steps=" + steps +
            '}';
    }
}
