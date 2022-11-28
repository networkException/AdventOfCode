package de.nwex.adventofcode.year2019.day09.parser.instruction.impl;

import de.nwex.adventofcode.year2019.day09.parser.instruction.Instruction;
import de.nwex.adventofcode.year2019.day09.parser.instruction.Parameter;
import java.util.List;

public class EndInstruction extends Instruction
{
    private EndImplementation implementation;

    public EndInstruction(EndImplementation implementation)
    {
        this.implementation = implementation;
    }

    @Override
    public Integer parameterCount()
    {
        return 0;
    }

    @Override
    public void run(List<Parameter> parameters)
    {
        if(implementation != null)
        {
            implementation.run();
        }
    }
}
