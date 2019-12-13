package de.jakobniklas.adventofcode.year2019.day09.parser.instruction.impl;

import de.jakobniklas.adventofcode.year2019.day09.parser.Parser;
import de.jakobniklas.adventofcode.year2019.day09.parser.instruction.Instruction;
import de.jakobniklas.adventofcode.year2019.day09.parser.instruction.Parameter;
import de.jakobniklas.applicationlib.commonutil.Log;

import java.util.List;

public class MultiplyInstruction extends Instruction
{
    @Override
    public Integer parameterCount()
    {
        return 3;
    }

    @Override
    public void run(List<Parameter> parameters)
    {
        Long before = memory.get(parameters.get(2));

        memory.set(parameters.get(2), memory.get(parameters.get(0)) * memory.get(parameters.get(1)));

        if(Parser.isDebug())
        {
            Log.print("Multiply", String.format("'%d' * '%d' = '%d' ('%d' before)", memory.get(parameters.get(0)), memory.get(parameters.get(1)), memory.get(parameters.get(2)), before));
        }
    }
}
