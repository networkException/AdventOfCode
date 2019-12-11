package de.jakobniklas.adventofcode.day11;

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
