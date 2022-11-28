package de.nwex.adventofcode.year2019.day02.computer.instruction;

import de.nwex.adventofcode.year2019.day02.computer.Computer;
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
    void run(List<Integer> parameters);
}
