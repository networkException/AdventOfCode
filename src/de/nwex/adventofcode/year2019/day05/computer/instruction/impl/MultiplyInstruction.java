package de.nwex.adventofcode.year2019.day05.computer.instruction.impl;

import de.nwex.adventofcode.year2019.day05.computer.Computer;
import de.nwex.adventofcode.year2019.day05.computer.instruction.Instruction;

/**
 * Class implementing an instruction
 *
 * @author networkException
 * @see #MultiplyInstruction(Computer)
 */
public class MultiplyInstruction extends Instruction
{
    /**
     * Takes 3 parameters. The first and the second one will be multiplied together and the resulting value be stored at
     * the address given in value 3 (always position mode). The first and second parameter can either me immediate or
     * position mode
     *
     * @param util A reference to the computer allowing memory and pointer modification
     */
    public MultiplyInstruction(Computer util)
    {
        super(3, (parameters) ->
        {
            util.setValue(parameters.get(2).getValue(), util.getParameter(parameters.get(0)) * util.getParameter(parameters.get(1)));
        });
    }
}
