package de.nwex.adventofcode.year2019.day11.instruction;

import de.nwex.adventofcode.year2019.day09.parser.instruction.Instruction;
import de.nwex.adventofcode.year2019.day09.parser.instruction.Parameter;

import java.util.List;

public class ImplementationOutputInstruction extends Instruction
{
    private OutputImplementation implementation;

    public ImplementationOutputInstruction(OutputImplementation implementation)
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
        implementation.run(memory.get(parameters.get(0)));
    }
}
