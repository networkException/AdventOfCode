package de.jakobniklas.adventofcode.day05.computer.instruction.impl;

import de.jakobniklas.adventofcode.day05.computer.Computer;
import de.jakobniklas.adventofcode.day05.computer.instruction.Instruction;

public class JumpIfFalseInstruction extends Instruction
{
    public JumpIfFalseInstruction(Computer util)
    {
        super(2, false, (parameters) ->
        {
            if(util.getParameter(parameters.get(0)) == 0)
            {
                util.setInstructionPointer(util.getParameter(parameters.get(1)));
            }
            else
            {
                util.addToInstructionPointer(3);
            }
        });
    }
}
