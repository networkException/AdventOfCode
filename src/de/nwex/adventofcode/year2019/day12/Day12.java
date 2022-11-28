package de.nwex.adventofcode.year2019.day12;

import de.nwex.adventofcode.framework.Day;
import de.nwex.adventofcode.year2019.day12.system.MoonSystem;

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
        return String.format("The iterations until the system repeats is '%d'", new MoonSystem(aPath).simulateSystem());
    }
}
