package de.jakobniklas.adventofcode.day06;

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
        OrbitMapper orbitMapper = new OrbitMapper("res/input.txt");

        Log.print("Day06/A", String.format("The number of orbits is '%d'", orbitMapper.calculate(false, false)));
        Log.print("Day06/B", String.format("The number of orbits is '%d'", orbitMapper.calculate(false, true)));
    }
}
