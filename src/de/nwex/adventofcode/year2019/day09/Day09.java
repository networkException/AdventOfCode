package de.nwex.adventofcode.year2019.day09;

import de.nwex.adventofcode.framework.Day;
import de.nwex.adventofcode.year2019.day09.parser.Parser;

public class Day09 extends Day
{
    public Day09()
    {
        aPath = "res/2019/day09.txt";
    }

    @Override
    public String partA(Boolean debug)
    {
        return String.format("The BOOST keycode in test mode is '%d'", new de.nwex.adventofcode.year2019.day09.parser.Parser(debug, 1L).parse(aPath));
    }

    @Override
    public String partB(Boolean debug)
    {
        return String.format("The BOOST distress signal is '%d'", new Parser(debug, 2L).parse(aPath));
    }
}
