package de.jakobniklas.adventofcode.year2019.day13.parser.instruction.impl;

import de.jakobniklas.adventofcode.year2019.day13.parser.instruction.Instruction;
import de.jakobniklas.adventofcode.year2019.day13.parser.instruction.Parameter;
import de.jakobniklas.applicationlib.commonutil.Log;

import java.util.List;

public class ConsoleOutputInstruction extends Instruction
{
    @Override
    public Integer parameterCount()
    {
        return 1;
    }

    @Override
    public void run(List<Parameter> parameters)
    {
        switch(parameters.get(0).getMode())
        {
            case POSITION: Log.print("Parser", String.format("Value at address '%d' is '%d'", parameters.get(0).getValue(), memory.get(parameters.get(0))));
                break;
            case IMMEDIATE: Log.print("Parser", String.format("Value is '%d'", memory.get(parameters.get(0))));
                break;
            case RELATIVE: Log.print("Parser", String.format("Value '%d' relative to '%d' is '%d'", parameters.get(0).getValue(), memory.getRelativeBase(), memory.get(parameters.get(0))));
                break;
        }
    }
}
