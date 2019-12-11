package de.jakobniklas.adventofcode.day09.parser.instruction.impl;

import de.jakobniklas.adventofcode.day09.parser.Parser;
import de.jakobniklas.adventofcode.day09.parser.instruction.Instruction;
import de.jakobniklas.adventofcode.day09.parser.instruction.Parameter;
import de.jakobniklas.applicationlib.commonutil.Log;

import java.util.List;

public class JumpIfFalseInstruction extends Instruction
{
    @Override
    public Integer parameterCount()
    {
        return 2;
    }

    @Override
    public void run(List<Parameter> parameters)
    {
        if(memory.get(parameters.get(0)).equals(0L))
        {
            memory.setPointer(memory.get(parameters.get(1)));

            if(Parser.isDebug())
            {
                Log.print("JumpIfFalse", String.format("'%d' == '%d'", memory.get(parameters.get(0)), memory.get(parameters.get(1))));
            }
        }
        else
        {
            if(Parser.isDebug())
            {
                Log.print("Equals", String.format("'%d' != '%d'", memory.get(parameters.get(0)), memory.get(parameters.get(1))));
            }
        }
    }
}