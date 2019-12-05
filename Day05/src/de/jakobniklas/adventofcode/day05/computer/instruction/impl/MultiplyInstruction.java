package de.jakobniklas.adventofcode.day05.computer.instruction.impl;

import de.jakobniklas.adventofcode.day05.computer.Computer;
import de.jakobniklas.adventofcode.day05.computer.instruction.Instruction;

public class MultiplyInstruction extends Instruction
{
    public MultiplyInstruction(Computer util)
    {
        super(3, (parameters) ->
        {
            util.setValue(parameters.get(2).getValue(), util.getParameter(parameters.get(0)) * util.getParameter(parameters.get(1)));
        });
    }
}
