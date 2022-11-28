package de.nwex.adventofcode.year2019.day06;

import de.nwex.adventofcode.framework.Day;
import de.nwex.adventofcode.year2019.day06.orbit.OrbitMapper;

public class Day06 extends Day
{
    private OrbitMapper orbitMapper;

    public Day06()
    {
        aPath = "res/2019/day06.txt";
    }

    @Override
    public String partA(Boolean debug)
    {
        if(orbitMapper == null)
        {
            orbitMapper = new OrbitMapper(aPath);
        }

        return String.format("The total number of orbits is '%d'", orbitMapper.calculate(debug, false));
    }

    @Override
    public String partB(Boolean debug)
    {
        if(orbitMapper == null)
        {
            orbitMapper = new OrbitMapper(aPath);
        }

        return String.format("The number of orbits from santa to you is '%d'", orbitMapper.calculate(false, true));
    }
}
