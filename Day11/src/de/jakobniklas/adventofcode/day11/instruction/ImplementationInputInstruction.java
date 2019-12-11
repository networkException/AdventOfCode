package de.jakobniklas.adventofcode.day11.instruction;

import de.jakobniklas.adventofcode.day09.parser.instruction.Instruction;
import de.jakobniklas.adventofcode.day09.parser.instruction.Parameter;

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
