package de.jakobniklas.adventofcode.year2019.day13;

import de.jakobniklas.adventofcode.framework.Day;
import de.jakobniklas.adventofcode.year2019.day13.parser.Parser;

public class Day13 extends Day
{
    public Day13()
    {
        aPath = "res/2019/day13.txt";
    }

    @Override
    public String partA(Boolean debug)
    {
        return String.format("The number of block tiles after the game exits is '%d'", new Parser(debug).part1(aPath));
    }

    @Override
    public String partB(Boolean debug)
    {
        new Parser(debug).part2(aPath);

        return "null";
    }
}
