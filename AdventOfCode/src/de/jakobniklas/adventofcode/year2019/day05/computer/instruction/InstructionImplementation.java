package de.jakobniklas.adventofcode.year2019.day05.computer.instruction;

import de.jakobniklas.adventofcode.year2019.day05.computer.Computer;

import java.util.List;

/**
 * Functional interface to be implemented when registering an instruction
 *
 * @author networkException
 * @see #run(List)
 * @see Computer#registerInstruction(int, Instruction) Computer#registerInstruction(int, Instruction)
 */
@FunctionalInterface
public interface InstructionImplementation
{
    /**
     * Method to be implemented
     */
    void run(List<Parameter> parameters);
}
