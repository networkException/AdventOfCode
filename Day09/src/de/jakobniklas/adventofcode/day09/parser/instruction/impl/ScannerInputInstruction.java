package de.jakobniklas.adventofcode.day09.parser.instruction.impl;

import de.jakobniklas.adventofcode.day09.parser.instruction.Instruction;
import de.jakobniklas.adventofcode.day09.parser.instruction.Parameter;
import de.jakobniklas.applicationlib.commonutil.Log;

import java.util.List;
import java.util.Scanner;

public class ScannerInputInstruction extends Instruction
{
    @Override
    public Integer parameterCount()
    {
        return 1;
    }

    @Override
    public void run(List<Parameter> parameters)
    {
        Scanner scanner = new Scanner(System.in);

        switch(parameters.get(0).getMode())
        {
            case POSITION:
                Log.print("Parser", String.format("Please input an integer to be put at address '%d' and press enter", parameters.get(0).getValue()));
                memory.set(parameters.get(0), scanner.nextLong());
                break;
            case RELATIVE:
                Log.print("Parser", String.format("Please input an integer to be put at '%d' relative to address '%d' and press enter", parameters.get(0).getValue(), memory.getRelativeBase()));
                memory.set(parameters.get(0), scanner.nextLong());
                break;
        }

        Log.measureTime("Start", "lol");

        scanner.close();
    }
}