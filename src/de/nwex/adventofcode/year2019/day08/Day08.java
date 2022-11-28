package de.nwex.adventofcode.year2019.day08;

import de.nwex.adventofcode.framework.Day;
import de.nwex.adventofcode.year2019.day08.image.Parser;

public class Day08 extends Day
{
    public Day08()
    {
        aPath = "res/2019/day08.txt";
    }

    @Override
    public String partA(Boolean debug)
    {
        return String.format("The amount of pixels(1) * pixels(2) in the layer with the fewest pixel(0) is '%d'", new Parser(aPath, 25, 6).getPartOneResult());
    }

    @Override
    public String partB(Boolean debug)
    {
        new Parser(aPath, 25, 6).printPartTwo(25, 6);

        return "The bios password is the code above";
    }
}
