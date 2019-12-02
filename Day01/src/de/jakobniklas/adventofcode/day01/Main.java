package de.jakobniklas.adventofcode.day01;

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
        Log.print("Day01", String.format("The total amount of fuel required is '%d'", FuelCalculator.calculate("res/input.txt")));
    }
}
