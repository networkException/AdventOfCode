package de.jakobniklas.adventofcode.day09;

import de.jakobniklas.adventofcode.day09.parser.Parser;

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
        new Parser(false).parse();
    }
}
