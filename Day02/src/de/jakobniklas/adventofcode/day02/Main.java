package de.jakobniklas.adventofcode.day02;

import de.jakobniklas.adventofcode.day02.computer.Computer;
import de.jakobniklas.applicationlib.commonutil.Log;

import java.util.List;

/**
 * Main class contacting entry point for JVM
 *
 * @author networkException
 * @see #main(String[])
 */
public class Main
{
    /**
     * JVM entry point
     *
     * @param args Program arguments (unused)
     */
    public static void main(String[] args)
    {
        Log.print("Day01/A (old)", String.valueOf(new IntcodeInterpreter("res/input.txt", true).interpret().get(0)));
        Log.print("Day01/A (new)", String.valueOf(new Computer("res/input.txt").compute(12, 2, false)));

        List<Integer> result = new Computer("res/input.txt").computeReverse(19690720, 0, 100, false);

        Log.print("Day01/B", String.valueOf(result.get(0) * 100 + result.get(1)));
    }
}
