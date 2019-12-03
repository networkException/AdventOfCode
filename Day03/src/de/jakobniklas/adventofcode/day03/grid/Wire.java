package de.jakobniklas.adventofcode.day03.grid;

/**
 * Class representing a wire which is on a point
 *
 * @author networkException
 * @see #wireId
 * @see #steps
 * @see #Wire(int, int)
 * @see #getWireId()
 * @see #getSteps()
 * @see #setSteps(int)
 */
public class Wire
{
    /**
     * The id of the wire on a given point (used to not count intersections with the same wire)
     */
    private int wireId;

    /**
     * The amount of steps already used to get to a given point from the origin
     */
    private int steps;

    /**
     * Creates a new wire with an id and an amount of steps
     *
     * @param wireId The id of the wire
     * @param steps  The amount of steps taken
     */
    public Wire(int wireId, int steps)
    {
        this.wireId = wireId;
        this.steps = steps;
    }

    /**
     * @return {@link #wireId}
     */
    public int getWireId()
    {
        return wireId;
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
}
