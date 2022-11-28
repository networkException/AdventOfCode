package de.nwex.adventofcode.year2019.day13.parser.instruction.impl;

import de.nwex.applicationlib.commonutil.Log;

import de.nwex.adventofcode.year2019.day13.parser.Parser;
import de.nwex.adventofcode.year2019.day13.parser.instruction.Instruction;
import de.nwex.adventofcode.year2019.day13.parser.instruction.Parameter;

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
