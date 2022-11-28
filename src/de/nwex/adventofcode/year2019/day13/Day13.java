package de.nwex.adventofcode.year2019.day13;

import de.nwex.adventofcode.framework.Day;
import de.nwex.adventofcode.year2019.day13.parser.Parser;

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
        return String.format("The score after destroying all balls is '%d'", new Parser(debug).part2(aPath));
    }
}
