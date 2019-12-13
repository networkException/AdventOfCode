package de.jakobniklas.adventofcode.year2019.day05.computer.instruction.impl;

import de.jakobniklas.adventofcode.year2019.day05.computer.Computer;
import de.jakobniklas.adventofcode.year2019.day05.computer.instruction.Instruction;

/**
 * Class implementing an instruction
 *
 * @author networkException
 * @see #LessThanInstruction(Computer)
 */
public class LessThanInstruction extends Instruction
{
    /**
     * Takes 3 parameters. If the value of the first (position and immediate) is less than the second, the value at the
     * address given in parameter 3 will be set to 1, otherwise it will be set to 0.
     *
     * @param util A reference to the computer allowing memory and pointer modification
     */
    public LessThanInstruction(Computer util)
    {
        super(3, (parameters) ->
        {
            if(util.getParameter(parameters.get(0)) < util.getParameter(parameters.get(1)))
            {
                util.setValue(parameters.get(2).getValue(), 1);
            }
            else
            {
                util.setValue(parameters.get(2).getValue(), 0);
            }
        });
    }
}
