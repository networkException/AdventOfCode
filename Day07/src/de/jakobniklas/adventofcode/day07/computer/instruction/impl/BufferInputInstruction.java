package de.jakobniklas.adventofcode.day07.computer.instruction.impl;

import de.jakobniklas.adventofcode.day07.computer.Computer;
import de.jakobniklas.adventofcode.day07.computer.instruction.Instruction;

import java.util.List;

/**
 * Class implementing an instruction
 *
 * @author networkException
 * @see #BufferInputInstruction(Computer, List)
 */
public class BufferInputInstruction extends Instruction
{
    public BufferInputInstruction(Computer util, List<Integer> buffer)
    {
        super(1, (parameters) -> util.setValue(parameters.get(0).getValue(), buffer.get(buffer.size() - 1)));
    }
}
