package de.jakobniklas.adventofcode.day05.computer.instruction.impl;

import de.jakobniklas.adventofcode.day05.computer.Computer;
import de.jakobniklas.adventofcode.day05.computer.instruction.Instruction;
import de.jakobniklas.applicationlib.commonutil.Log;

import java.util.Scanner;

/**
 * Class implementing an instruction
 *
 * @author networkException
 * @see #InputInstruction(Computer)
 */
public class InputInstruction extends Instruction
{
    /**
     * Takes one parameter. The value at the address of the first parameter (always position mode) will be set to an
     * integer value given by the user. This instruction will open a scanner an take the next integer from the {@link
     * System#in} inputStream, will pause the processing while not confirmed with enter
     *
     * @param util A reference to the computer allowing memory and pointer modification
     */
    public InputInstruction(Computer util)
    {
        super(1, (parameters) ->
        {
            Log.print("Computer", String.format("Please input an integer to be put at address '%d' and press enter", parameters.get(0).getValue()));
            Scanner scanner = new Scanner(System.in);
            util.setValue(parameters.get(0).getValue(), scanner.nextInt());
            scanner.close();
        });
    }
}
