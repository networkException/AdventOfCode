package de.jakobniklas.adventofcode.year2019.day12;

import de.jakobniklas.adventofcode.framework.Day;
import de.jakobniklas.adventofcode.year2019.day12.system.MoonSystem;

public class Day12 extends Day
{
    public Day12()
    {
        aPath = "res/2019/day12.txt";
    }

    @Override
    public String partA(Boolean debug)
    {
        return String.format("The total energy of the moons after 1000 calculations is '%d'", new MoonSystem(aPath).getTotalEnergy());
    }

    @Override
    public String partB(Boolean debug)
    {
        return "null";
    }
}
