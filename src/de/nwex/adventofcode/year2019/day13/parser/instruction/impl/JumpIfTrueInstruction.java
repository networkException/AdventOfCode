package de.nwex.adventofcode.year2019.day13.parser.instruction.impl;

import de.nwex.adventofcode.year2019.day13.parser.instruction.Instruction;
import de.nwex.adventofcode.year2019.day13.parser.instruction.Parameter;

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
