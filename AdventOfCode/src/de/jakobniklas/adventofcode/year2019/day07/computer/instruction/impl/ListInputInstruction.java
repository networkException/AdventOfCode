package de.jakobniklas.adventofcode.year2019.day07.computer.instruction.impl;

import de.jakobniklas.adventofcode.year2019.day07.computer.Computer;
import de.jakobniklas.adventofcode.year2019.day07.computer.instruction.Instruction;

import java.util.List;

/**
 * Class implementing an instruction
 *
 * @author networkException
 * @see #ListInputInstruction(Computer, List)
 */
public class ListInputInstruction extends Instruction
{
    /**
     * Takes one parameter. The value at the address of the first parameter (always position mode) will be set to an
     * integer value given by the a list of value. This instruction will get a value from the list of integers. The
     * position of the value taken from that list will be increased every call
     *
     * @param util A reference to the computer allowing memory and pointer modification
     */
    public ListInputInstruction(Computer util, List<Integer> values)
    {
        super(1, (parameters) ->
        {
            util.setValue(parameters.get(0).getValue(), values.get(pointer));

            pointer++;
        });
    }

    private static int pointer = 0;

    public static void resetPointer()
    {
        pointer = 0;
    }
}
