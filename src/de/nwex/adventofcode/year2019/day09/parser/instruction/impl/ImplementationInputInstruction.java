package de.nwex.adventofcode.year2019.day09.parser.instruction.impl;

import de.nwex.adventofcode.year2019.day09.parser.instruction.Instruction;
import de.nwex.adventofcode.year2019.day09.parser.instruction.Parameter;

import java.util.List;

public class ImplementationInputInstruction extends Instruction
{
    private InputImplementation implementation;

    public ImplementationInputInstruction(InputImplementation implementation)
    {
        this.implementation = implementation;
    }

    @Override
    public Integer parameterCount()
    {
        return 1;
    }

    @Override
    public void run(List<Parameter> parameters)
    {
        memory.set(parameters.get(0), implementation.run());
    }
}
