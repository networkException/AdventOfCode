package de.nwex.adventofcode.year2019.day11;

import de.nwex.adventofcode.framework.Day;

public class Day11 extends Day
{
    public Day11()
    {
        aPath = "res/2019/day11.txt";
    }

    @Override
    public String partA(Boolean debug)
    {
        return String.format("The number of unique painted fields is '%d'", new de.nwex.adventofcode.year2019.day11.Parser(debug, false).parse(aPath));
    }

    @Override
    public String partB(Boolean debug)
    {
        Parser parser = new Parser(debug, true);
        parser.parse(aPath);
        parser.print();

        return "Code on the hull is a combination of the 8 letters above";
    }
}
