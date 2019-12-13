package de.jakobniklas.adventofcode.year2019.day13.parser.instruction.impl;

import de.jakobniklas.adventofcode.year2019.day13.parser.instruction.Instruction;
import de.jakobniklas.adventofcode.year2019.day13.parser.instruction.Parameter;

import java.util.List;

public class LessThanInstruction extends Instruction
{
    @Override
    public Integer parameterCount()
    {
        return 3;
    }

    @Override
    public void run(List<Parameter> parameters)
    {
        memory.set(parameters.get(2), memory.get(parameters.get(0)) < memory.get(parameters.get(1)) ? 1L : 0L);
    }
}
