package de.jakobniklas.adventofcode.day05;

import de.jakobniklas.adventofcode.day05.computer.Computer;
import de.jakobniklas.applicationlib.commonutil.Log;

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
        Log.print("Day05", "Use code '1' for part 1 and code '5' for part 2");
        Log.print("Day05", String.format("The output is '%d'", new Computer("res/input.txt").compute(false).get(223)));
    }
}
