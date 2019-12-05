package de.jakobniklas.adventofcode.day05.computer.instruction.impl;

import de.jakobniklas.adventofcode.day05.computer.Computer;
import de.jakobniklas.adventofcode.day05.computer.instruction.Instruction;

public class LessThanInstruction extends Instruction
{
    public LessThanInstruction(Computer util)
    {
        super(3, (parameters) ->
        {
            if(util.getParameter(parameters.get(0)) < util.getParameter(parameters.get(1)))
            {
                util.setValue(parameters.get(2).getValue(), 1);
            }
            else
            {
                util.setValue(parameters.get(2).getValue(), 0);
            }
        });
    }
}
