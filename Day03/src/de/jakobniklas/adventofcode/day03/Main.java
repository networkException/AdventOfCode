package de.jakobniklas.adventofcode.day03;

import de.jakobniklas.adventofcode.day03.grid.Grid;
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
        Log.print("Day03/A", String.format("The distance to the nearest intersection (air) is '%d' fields away", new Grid("res/input.txt").nearestCrossSection(true)));
        Log.print("Day03/B", String.format("The distance to the nearest intersection (steps) is '%d' steps away", new Grid("res/input.txt").nearestCrossSection(false)));
    }
}
