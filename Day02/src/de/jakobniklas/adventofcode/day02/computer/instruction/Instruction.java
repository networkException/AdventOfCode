package de.jakobniklas.adventofcode.day02.computer.instruction;

import java.util.List;

/**
 * Class storing the values of an instruction
 *
 * @author networkException
 * @see #implementation
 * @see #parameterCount
 * @see #Instruction(int, InstructionImplementation)
 * @see #process(List)
 * @see #getParameterCount()
 */
public class Instruction
{
    /**
     * The implementation of the instruction, stores the functionality
     */
    private InstructionImplementation implementation;

    /**
     * The amount of parameters the instruction takes
     */
    private int parameterCount;

    /**
     * Creates a new instruction with a given amount of parameters and the instructions functionality
     *
     * @param parameterCount The amount of parameters
     * @param implementation The instructions functionality (can be a lambda expression)
     */
    public Instruction(int parameterCount, InstructionImplementation implementation)
    {
        this.implementation = implementation;
        this.parameterCount = parameterCount;
    }

    /**
     * Processes the implementation
     *
     * @param parameters The values in the memory which are parameters for the instruction
     */
    public void process(List<Integer> parameters)
    {
        implementation.run(parameters);
    }

    /**
     * @return {@link #parameterCount}
     */
    public int getParameterCount()
    {
        return parameterCount;
    }
}
