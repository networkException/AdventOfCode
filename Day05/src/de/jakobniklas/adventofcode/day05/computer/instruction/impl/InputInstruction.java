package de.jakobniklas.adventofcode.day05.computer.instruction.impl;

import de.jakobniklas.adventofcode.day05.computer.Computer;
import de.jakobniklas.adventofcode.day05.computer.instruction.Instruction;
import de.jakobniklas.applicationlib.commonutil.Log;

import java.util.Scanner;

public class InputInstruction extends Instruction
{
    public InputInstruction(Computer util)
    {
        super(1, (parameters) ->
        {
            Log.print("Computer", String.format("Please input an integer to be put at address '%d'", parameters.get(0).getValue()));
            Scanner scanner = new Scanner(System.in);
            util.setValue(parameters.get(0).getValue(), scanner.nextInt());
            scanner.close();
        });
    }
}
