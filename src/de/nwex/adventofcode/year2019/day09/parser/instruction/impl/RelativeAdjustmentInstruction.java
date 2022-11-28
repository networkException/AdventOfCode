package de.nwex.adventofcode.year2019.day09.parser.instruction.impl;

import de.nwex.adventofcode.year2019.day09.parser.instruction.Instruction;
import de.nwex.adventofcode.year2019.day09.parser.instruction.Parameter;

import de.nwex.applicationlib.commonutil.Log;

import de.nwex.adventofcode.year2019.day09.parser.Parser;
import java.util.List;

public class RelativeAdjustmentInstruction extends Instruction
{
    @Override
    public Integer parameterCount()
    {
        return 1;
    }

    @Override
    public void run(List<Parameter> parameters)
    {
        memory.addToRelative(memory.get(parameters.get(0)));

        if(Parser.isDebug())
        {
            Log.print(memory.getRelativeBase() + " (" + parameters.toString() + ")");
        }
    }
}
