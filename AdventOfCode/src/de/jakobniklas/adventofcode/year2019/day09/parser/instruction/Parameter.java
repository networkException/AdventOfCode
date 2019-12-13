package de.jakobniklas.adventofcode.year2019.day09.parser.instruction;

/**
 * Class storing the mode and value of a parameter
 *
 * @author networkException
 * @see #mode
 * @see #value
 * @see #Parameter(ParameterMode, Long)
 * @see #getMode()
 * @see #getValue()
 * @see #toString()
 */
public class Parameter
{
    /**
     * The mode of the parameter (how the computer interprets the value, specified in the opcode)
     */
    private ParameterMode mode;

    /**
     * The value of the parameter
     */
    private Long value;

    /**
     * Creates a new parameter and stores mode as well as value
     *
     * @param mode  The mode to be set
     * @param value The value of the parameter
     */
    public Parameter(ParameterMode mode, Long value)
    {
        this.mode = mode;
        this.value = value;
    }

    /**
     * @return {@link #mode}
     */
    public ParameterMode getMode()
    {
        return mode;
    }

    /**
     * @return {@link #value}
     */
    public Long getValue()
    {
        return value;
    }

    /**
     * @return A string interpretation of the object
     */
    @Override
    public String toString()
    {
        return "{" +
            "mode=" + mode +
            ", value=" + value +
            '}';
    }
}
