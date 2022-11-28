package de.nwex.adventofcode.year2019.day13.parser.instruction.impl;

import de.nwex.applicationlib.commonutil.Log;

import de.nwex.adventofcode.year2019.day13.parser.Parser;
import de.nwex.adventofcode.year2019.day13.parser.instruction.Instruction;
import de.nwex.adventofcode.year2019.day13.parser.instruction.Parameter;

import java.util.List;

public class EqualsInstruction extends Instruction
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

        memory.set(parameters.get(2), memory.get(parameters.get(0)).equals(memory.get(parameters.get(1))) ? 1L : 0L);

        if(Parser.isDebug())
        {
            Log.print("Equals", String.format("'%d' == '%d' ? '%d' ('%d' before)", memory.get(parameters.get(0)), memory.get(parameters.get(1)), memory.get(parameters.get(2)), before));
        }
    }
}
