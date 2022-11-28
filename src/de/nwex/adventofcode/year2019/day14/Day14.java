package de.nwex.adventofcode.year2019.day14;

import de.nwex.adventofcode.framework.Day;
import de.nwex.adventofcode.year2019.day14.nanofactory.Parser;

public class Day14 extends Day
{
    public Day14()
    {
        aPath = "res/2019/day14.txt";
    }

    @Override
    public String partA(Boolean debug)
    {
        new Parser(aPath).part1();

        return "null";
    }

    @Override
    public String partB(Boolean debug)
    {
        return "null";
    }
}
