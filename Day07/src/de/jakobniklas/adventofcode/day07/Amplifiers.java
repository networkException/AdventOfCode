package de.jakobniklas.adventofcode.day07;

import de.jakobniklas.adventofcode.day07.computer.Computer;
import de.jakobniklas.adventofcode.day07.computer.instruction.Instruction;
import de.jakobniklas.adventofcode.day07.computer.instruction.impl.ListInputInstruction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Amplifiers
{
    public static int getThrusterOutput(int phaseA, int phaseB, int phaseC, int phaseD, int phaseE)
    {
        ListInputInstruction.resetPointer();

        AtomicInteger output = new AtomicInteger(0);
        Computer computer = new Computer("res/software.txt");
        computer.registerInstruction(4, new Instruction(1, (parameters) -> output.set(computer.getParameter(parameters.get(0)))));

        IntStream.range(0, 5).forEach((i) ->
        {
            computer.unregisterInstruction(3);
            computer.registerInstruction(3, new ListInputInstruction(computer, Arrays.asList(phaseA, output.get(), phaseB, output.get(), phaseC, output.get(), phaseD, output.get(), phaseE, output.get())));
            computer.compute(false);
        });

        return output.get();
    }

    public static int highest()
    {
        AtomicInteger highest = new AtomicInteger(0);

        IntStream.range(0, 5).forEach((a) -> IntStream.range(0, 5).forEach((b) -> IntStream.range(0, 5).forEach((c) -> IntStream.range(0, 5).forEach((d) -> IntStream.range(0, 5).forEach((e) ->
        {
            if(hasDistinctDigits(String.format("%d%d%d%d%d", a, b, c, d, e)))
            {
                int thrusters = getThrusterOutput(a, b, c, d, e);

                if(thrusters > highest.get())
                {
                    highest.set(thrusters);
                }
            }
        })))));

        return highest.get();
    }

    public static boolean hasDistinctDigits(String input)
    {
        List<Integer> digits = new ArrayList<>();

        for(Character character : input.toCharArray())
        {
            int digit = Integer.parseInt(String.valueOf(character));

            if(digits.indexOf(digit) == -1)
            {
                digits.add(digit);
            }
            else
            {
                return false;
            }
        }

        return true;
    }
}
