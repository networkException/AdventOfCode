package de.jakobniklas.adventofcode.day05.computer.instruction.impl;

import de.jakobniklas.adventofcode.day05.computer.Computer;
import de.jakobniklas.adventofcode.day05.computer.instruction.Instruction;

/**
 * Class implementing an instruction
 *
 * @author networkException
 * @see #JumpIfTrueInstruction(Computer)
 */
public class JumpIfTrueInstruction extends Instruction
{
    public JumpIfTrueInstruction(Computer util)
    {
        /**
         * Takes 2 parameters. The first parameter specifies an input value (immediate or position mode). If the input value
         * is not 0, the instructionPointer will be set to the value given from parameter 2 (also position or immediate). If the
         * first value is 0, the computer will just skip over the instruction
         *
         * @param util A reference to the computer allowing memory and pointer modification
         */
        super(2, false, (parameters) ->
        {
            if(util.getParameter(parameters.get(0)) != 0)
            {
                util.setInstructionPointer(util.getParameter(parameters.get(1)));
            }
            else
            {
                util.addToInstructionPointer(3);
            }
        });
    }
}
