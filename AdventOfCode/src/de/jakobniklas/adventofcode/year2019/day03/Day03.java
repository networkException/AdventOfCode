package de.jakobniklas.adventofcode.year2019.day03;

import de.jakobniklas.adventofcode.framework.Day;
import de.jakobniklas.adventofcode.year2019.day03.grid.Grid;

public class Day03 extends Day
{
    public Day03()
    {
        aPath = "res/2019/day03.txt";
    }

    @Override
    public String partA(Boolean debug)
    {
        return String.format("The distance to the nearest intersection (air) is '%d' fields away", new Grid(aPath).nearestCrossSection(true));
    }

    @Override
    public String partB(Boolean debug)
    {
        return String.format("The distance to the nearest intersection (steps) is '%d' steps away", new Grid(aPath).nearestCrossSection(false));
    }
}
