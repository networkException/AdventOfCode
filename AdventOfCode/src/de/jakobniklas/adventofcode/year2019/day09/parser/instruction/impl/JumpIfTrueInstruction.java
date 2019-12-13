package de.jakobniklas.adventofcode.year2019.day09.parser.instruction.impl;

import de.jakobniklas.adventofcode.year2019.day09.parser.instruction.Instruction;
import de.jakobniklas.adventofcode.year2019.day09.parser.instruction.Parameter;

import java.util.List;

public class JumpIfTrueInstruction extends Instruction
{
    @Override
    public Integer parameterCount()
    {
        return 2;
    }

    @Override
    public void run(List<Parameter> parameters)
    {
        if(!memory.get(parameters.get(0)).equals(0L))
        {
            memory.setPointer(memory.get(parameters.get(1)));
        }
    }
}
