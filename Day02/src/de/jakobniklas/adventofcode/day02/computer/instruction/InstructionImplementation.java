package de.jakobniklas.adventofcode.day02.computer.instruction;

import java.util.List;

/**
 * Functional interface to be implemented when registering an instruction
 *
 * @author networkException
 * @see #run(List)
 * @see de.jakobniklas.adventofcode.day02.computer.Computer#registerInstruction(int, Instruction)
 * Computer#registerInstruction(int, Instruction)
 */
@FunctionalInterface
public interface InstructionImplementation
{
    /**
     * Method to be implemented
     */
    void run(List<Integer> parameters);
}
