package de.nwex.adventofcode.year2019.day02;

import de.nwex.adventofcode.framework.Day;
import de.nwex.adventofcode.year2019.day02.computer.Computer;

import java.util.List;

public class Day02 extends Day
{
    public Day02()
    {
        aPath = "res/2019/day02.txt";
    }

    @Override
    public String partA(Boolean debug)
    {
        return String.format("Computed value for noun 12 and verb 2 '%d'", new Computer(aPath).compute(12, 2, debug));
    }

    @Override
    public String partB(Boolean debug)
    {
        List<Integer> result = new Computer(aPath).computeReverse(19690720, 0, 100, debug);

        return String.format("Found nouns for output '19690720': %d * 100 + %d = %d", result.get(0), result.get(1), result.get(0) * 100 + result.get(1));
    }
}
