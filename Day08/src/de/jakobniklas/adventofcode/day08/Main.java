package de.jakobniklas.adventofcode.day08;

import de.jakobniklas.adventofcode.day08.image.Parser;
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
        Log.print("Day08/A", String.valueOf(new Parser("res/input.txt", 25, 6).getPartOneResult()));
        Log.print("Day08/B", ":");
        new Parser("res/input.txt", 25, 6).printPartTwo(25, 6);
    }
}
