package de.jakobniklas.adventofcode.day10;

import de.jakobniklas.adventofcode.day10.astroids.Parser;

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
        new Parser("res/input.txt").parse();
    }
}
