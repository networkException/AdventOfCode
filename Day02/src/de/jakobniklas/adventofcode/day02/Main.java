package de.jakobniklas.adventofcode.day02;

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
        Log.print("Day01/A", String.valueOf(new IntcodeInterpreter("res/input.txt", true).interpret().get(0)));
    }
}
