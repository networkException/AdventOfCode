package de.jakobniklas.adventofcode.year2019.day13.parser.instruction.impl;

import de.jakobniklas.adventofcode.year2019.day13.parser.instruction.Instruction;
import de.jakobniklas.adventofcode.year2019.day13.parser.instruction.Parameter;
import de.jakobniklas.applicationlib.commonutil.Log;

import java.util.List;
import java.util.Scanner;

public class ImplementationAndScannerInputInstruction extends Instruction
{
    private InputImplementation implementation;

    public ImplementationAndScannerInputInstruction(InputImplementation implementation)
    {
        this.implementation = implementation;
    }

    @Override
    public Integer parameterCount()
    {
        return 1;
    }

    @Override
    public void run(List<Parameter> parameters)
    {
        implementation.run();

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

        //scanner.close();
    }
}
