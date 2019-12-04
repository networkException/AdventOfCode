package de.jakobniklas.adventofcode.day04;

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
        Log.print("Day04/A", String.valueOf(Password.find(240920, 789857, false).size()));
        Log.print("Day04/B", String.valueOf(Password.find(240920, 789857, true).size()));
    }
}
