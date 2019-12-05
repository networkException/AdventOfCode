package de.jakobniklas.adventofcode.day05.computer.instruction.impl;

import de.jakobniklas.adventofcode.day05.computer.Computer;
import de.jakobniklas.adventofcode.day05.computer.instruction.Instruction;
import de.jakobniklas.applicationlib.commonutil.Log;

public class OutputInstruction extends Instruction
{
    public OutputInstruction(Computer util)
    {
        super(1, (parameters) ->
        {
            Log.print("Computer", String.format("Value at address '%d' is '%d'", parameters.get(0).getValue(), util.getParameter(parameters.get(0))));
        });
    }
}
