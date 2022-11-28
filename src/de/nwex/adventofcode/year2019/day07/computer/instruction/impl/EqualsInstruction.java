package de.nwex.adventofcode.year2019.day07.computer.instruction.impl;

import de.nwex.adventofcode.year2019.day07.computer.Computer;
import de.nwex.adventofcode.year2019.day07.computer.instruction.Instruction;

/**
 * Class implementing an instruction
 *
 * @author networkException
 * @see #EqualsInstruction(de.nwex.adventofcode.year2019.day07.computer.Computer)
 */
public class EqualsInstruction extends Instruction
{
    /**
     * Takes 3 parameters. The first and second will be compared, if they are equal the value at the address given in
     * parameter three (always position mode) will be set to 1, otherwise to 0. The first and second parameter can
     * either me immediate or position mode
     *
     * @param util A reference to the computer allowing memory and pointer modification
     */
    public EqualsInstruction(Computer util)
    {
        super(3, (parameters) ->
        {
            if(util.getParameter(parameters.get(0)) == util.getParameter(parameters.get(1)))
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
