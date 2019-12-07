package de.jakobniklas.adventofcode.day07.computer.instruction;

import java.util.List;

/**
 * Class storing the values of an instruction
 *
 * @author networkException
 * @see #implementation
 * @see #parameterCount
 * @see #increasePointerByParamCount
 * @see #Instruction(int, boolean, InstructionImplementation)
 * @see #Instruction(int, InstructionImplementation)
 * @see #process(List)
 * @see #getParameterCount()
 * @see #isIncreasePointerByParamCount()
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
     * If the pointer should automatically be increased after the instruction has processed (should be false if the
     * instruction sets the pointer itself, for example jump-if)
     */
    private boolean increasePointerByParamCount;

    /**
     * Creates a new instruction with a given amount of parameters and the instructions functionality
     *
     * @param parameterCount              The amount of parameters
     * @param increasePointerByParamCount If the pointer should automatically be increased after the instruction has
     *                                    processed (should be false if the instruction sets the pointer itself, for
     *                                    example jump-if)
     * @param implementation              The instructions functionality (can be a lambda expression)
     */
    public Instruction(int parameterCount, boolean increasePointerByParamCount, InstructionImplementation implementation)
    {
        this.implementation = implementation;
        this.parameterCount = parameterCount;
        this.increasePointerByParamCount = increasePointerByParamCount;
    }

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
        this.increasePointerByParamCount = true;
    }

    /**
     * Processes the implementation
     *
     * @param parameters The values in the memory which are parameters for the instruction
     */
    public void process(List<Parameter> parameters)
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

    /**
     * @return {@link #increasePointerByParamCount}
     */
    public boolean isIncreasePointerByParamCount()
    {
        return increasePointerByParamCount;
    }
}
