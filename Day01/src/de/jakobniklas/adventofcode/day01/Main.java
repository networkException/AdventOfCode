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
        Log.print("Day01/A", String.format("The total amount of fuel required is '%d'", FuelCalculator.calculate("res/sammy.txt", false)));
        Log.print("Day01/B", String.format("The total amount of fuel required, while also calculating the amount of fuel required for a given amount of fuel is '%d'", FuelCalculator.calculate("res/input.txt", true)));
    }
}
