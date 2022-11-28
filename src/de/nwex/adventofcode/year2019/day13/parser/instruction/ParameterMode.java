package de.nwex.adventofcode.year2019.day13.parser.instruction;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum storing modes which define how a parameter should be interpreted
 *
 * @author networkException
 */
public enum ParameterMode
{
    POSITION(0),

    IMMEDIATE(1),

    RELATIVE(2);

    /**
     * A map of ids and their modes
     */
    private static Map<Integer, ParameterMode> map = new HashMap<>();

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
     * The id of the parameterMode (as specified in the opcode)
     */
    private int id;

    /**
     * The enum constructor, defining that every mode has an id
     *
     * @param id The id of the mode
     */
    ParameterMode(int id)
    {
        this.id = id;
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
