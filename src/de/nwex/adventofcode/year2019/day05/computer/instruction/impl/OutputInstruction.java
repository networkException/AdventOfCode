package de.nwex.adventofcode.year2019.day05.computer.instruction.impl;

import de.nwex.adventofcode.year2019.day05.computer.Computer;
import de.nwex.adventofcode.year2019.day05.computer.instruction.Instruction;

/**
 * Class implementing an instruction
 *
 * @author networkException
 * @see #OutputInstruction(Computer)
 */
public class OutputInstruction extends Instruction
{
    /**
     * Takes 1 parameter. Outputs the value given in parameter 1 (position or immediate) to the console
     *
     * @param util A reference to the computer allowing memory and pointer modification
     */
    public OutputInstruction(Computer util)
    {
        super(1, (parameters) ->
        {
            //Log.print("Computer", String.format("Value at address '%d' is '%d'", parameters.get(0).getValue(), util.getParameter(parameters.get(0))));
        });
    }
}
