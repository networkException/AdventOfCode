package de.jakobniklas.adventofcode.day07.computer.instruction;

import de.jakobniklas.adventofcode.day07.computer.Computer;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum storing modes which define how a parameter should be interpreted
 *
 * @author networkException
 * @see #POSITION
 * @see #IMMEDIATE
 * @see #id
 * @see #map
 * @see #ParameterMode(int)
 * @see #valueOf(int)
 * @see #getDefault()
 * @see #getId()
 */
public enum ParameterMode
{
    /**
     * The value of an argument will be interpreted as a pointer to a memory address. The output value from {@link
     * Computer#getParameter(Parameter) Computer#getParameter(Parameter)} will return the value at that position
     */
    POSITION(0),

    /**
     * The value of an argument will be interpreted as the output value directly
     */
    IMMEDIATE(1);

    /**
     * The id of the parameterMode (as specified in the opcode)
     */
    private int id;

    /**
     * A map of ids and their modes
     */
    private static Map<Integer, ParameterMode> map = new HashMap<>();

    /**
     * The enum constructor, defining that every mode has an id
     *
     * @param id The id of the mode
     */
    ParameterMode(int id)
    {
        this.id = id;
    }

    /*
     * Fills {@link #map} on class load
     */
    static
    {
        for(ParameterMode pageType : ParameterMode.values())
        {
            map.put(pageType.id, pageType);
        }
    }

    /**
     * Returns the mode for a given id
     *
     * @param id The if of the mode
     *
     * @return The corresponding mode
     */
    public static ParameterMode valueOf(int id)
    {
        return map.get(id);
    }

    /**
     * @return The default mode used for an parameter
     */
    public static ParameterMode getDefault()
    {
        return POSITION;
    }

    /**
     * @return The id of a mode
     */
    public int getId()
    {
        return id;
    }
}
