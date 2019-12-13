package de.jakobniklas.adventofcode.year2019.day07;

import de.jakobniklas.adventofcode.year2019.day07.computer.Computer;
import de.jakobniklas.adventofcode.year2019.day07.computer.instruction.Instruction;
import de.jakobniklas.adventofcode.year2019.day07.computer.instruction.impl.ListInputInstruction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Amplifiers
{
    public static int getLoopedOutput(String path, int phaseA, int phaseB, int phaseC, int phaseD, int phaseE, boolean debug)
    {
        //The last output from any amp
        AtomicInteger lastOutput = new AtomicInteger(0);

        //A pointer keeping track of the value which should be set when opcode 3 (input) gets called
        AtomicInteger aInputPointer = new AtomicInteger(0);
        AtomicInteger bInputPointer = new AtomicInteger(0);
        AtomicInteger cInputPointer = new AtomicInteger(0);
        AtomicInteger dInputPointer = new AtomicInteger(0);
        AtomicInteger eInputPointer = new AtomicInteger(0);

        Computer ampA = new Computer(path);
        Computer ampB = new Computer(path);
        Computer ampC = new Computer(path);
        Computer ampD = new Computer(path);
        Computer ampE = new Computer(path);

        //Set input behavior
        ampA.registerInstruction(3, new Instruction(1, parameters ->
        {
            switch(aInputPointer.get())
            {
                //First round, phase input
                case 0: ampA.setValue(parameters.get(0).getValue(), phaseA); break;

                //First round, amplifier's input value
                case 1: ampA.setValue(parameters.get(0).getValue(), 0); break;

                //Output from ampE
                default: ampA.setValue(parameters.get(0).getValue(), lastOutput.get());
            }

            aInputPointer.getAndIncrement();
        }));

        ampB.registerInstruction(3, new Instruction(1, parameters ->
        {
            switch(bInputPointer.get())
            {
                //First round, phase input
                case 0: ampB.setValue(parameters.get(0).getValue(), phaseB); break;

                //Output from ampA
                default: ampB.setValue(parameters.get(0).getValue(), lastOutput.get());
            }

            bInputPointer.getAndIncrement();
        }));

        ampC.registerInstruction(3, new Instruction(1, parameters ->
        {
            switch(cInputPointer.get())
            {
                //First round, phase input
                case 0: ampC.setValue(parameters.get(0).getValue(), phaseC); break;

                //Output from ampB
                default: ampC.setValue(parameters.get(0).getValue(), lastOutput.get());
            }

            cInputPointer.getAndIncrement();
        }));

        ampD.registerInstruction(3, new Instruction(1, parameters ->
        {
            switch(dInputPointer.get())
            {
                //First round, phase input
                case 0: ampD.setValue(parameters.get(0).getValue(), phaseD); break;

                //Output from ampC
                default: ampD.setValue(parameters.get(0).getValue(), lastOutput.get());
            }

            dInputPointer.getAndIncrement();
        }));

        ampE.registerInstruction(3, new Instruction(1, parameters ->
        {
            switch(eInputPointer.get())
            {
                //First round, phase input
                case 0: ampE.setValue(parameters.get(0).getValue(), phaseE); break;

                //Output from ampD
                default: ampE.setValue(parameters.get(0).getValue(), lastOutput.get());
            }

            eInputPointer.getAndIncrement();
        }));

        //Set output behavior
        ampA.registerInstruction(4, new Instruction(1, parameters ->
        {
            //Ends the calculation
            ampA.end();

            //Sets the output value to the input for the next amp
            lastOutput.set(ampA.getParameter(parameters.get(0)));
        }));

        ampB.registerInstruction(4, new Instruction(1, parameters ->
        {
            //Ends the calculation
            ampB.end();

            //Sets the output value to the input for the next amp
            lastOutput.set(ampB.getParameter(parameters.get(0)));
        }));

        ampC.registerInstruction(4, new Instruction(1, parameters ->
        {
            //Ends the calculation
            ampC.end();

            //Sets the output value to the input for the next amp
            lastOutput.set(ampC.getParameter(parameters.get(0)));
        }));

        ampD.registerInstruction(4, new Instruction(1, parameters ->
        {
            //Ends the calculation
            ampD.end();

            //Sets the output value to the input for the next amp
            lastOutput.set(ampD.getParameter(parameters.get(0)));
        }));

        ampE.registerInstruction(4, new Instruction(1, parameters ->
        {
            //Ends the calculation
            ampE.end();

            //Sets the output value to the input for the next amp
            lastOutput.set(ampE.getParameter(parameters.get(0)));
        }));

        AtomicBoolean ended = new AtomicBoolean(false);

        ampE.setEndImplementation(() -> ended.set(true));

        while(!ended.get())
        {
            ampA.compute(debug, false);
            ampA.setInitialPointer(ampA.getInstructionPointer());

            ampB.compute(debug, false);
            ampB.setInitialPointer(ampB.getInstructionPointer());

            ampC.compute(debug, false);
            ampC.setInitialPointer(ampC.getInstructionPointer());

            ampD.compute(debug, false);
            ampD.setInitialPointer(ampD.getInstructionPointer());

            ampE.compute(debug, false);
            ampE.setInitialPointer(ampE.getInstructionPointer());
        }

        return lastOutput.get();
    }


    public static int getThrusterOutput(String path, int phaseA, int phaseB, int phaseC, int phaseD, int phaseE)
    {
        ListInputInstruction.resetPointer();

        AtomicInteger output = new AtomicInteger(0);
        Computer computer = new Computer(path);
        computer.registerInstruction(4, new Instruction(1, (parameters) -> output.set(computer.getParameter(parameters.get(0)))));

        IntStream.range(0, 5).forEach((i) ->
        {
            computer.unregisterInstruction(3);
            computer.registerInstruction(3, new ListInputInstruction(computer, Arrays.asList(phaseA, output.get(), phaseB, output.get(), phaseC, output.get(), phaseD, output.get(), phaseE, output.get())));
            computer.compute(false, true);
        });

        return output.get();
    }

    public static int highest(String path, int from, int to)
    {
        AtomicInteger highest = new AtomicInteger(0);

        IntStream.range(from, to).forEach((a) -> IntStream.range(from, to).forEach((b) -> IntStream.range(from, to).forEach((c) -> IntStream.range(from, to).forEach((d) -> IntStream.range(from, to).forEach((e) ->
        {
            if(hasDistinctDigits(String.format("%d%d%d%d%d", a, b, c, d, e)))
            {
                int thrusters = getThrusterOutput(path, a, b, c, d, e);

                if(thrusters > highest.get())
                {
                    highest.set(thrusters);
                }
            }
        })))));

        return highest.get();
    }

    public static int highestLooped(String path, int from, int to, boolean debug)
    {
        AtomicInteger highest = new AtomicInteger(0);

        IntStream.range(from, to).forEach((a) -> IntStream.range(from, to).forEach((b) -> IntStream.range(from, to).forEach((c) -> IntStream.range(from, to).forEach((d) -> IntStream.range(from, to).forEach((e) ->
        {
            if(hasDistinctDigits(String.format("%d%d%d%d%d", a, b, c, d, e)))
            {
                int thrusters = getLoopedOutput(path, a, b, c, d, e, debug);

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
