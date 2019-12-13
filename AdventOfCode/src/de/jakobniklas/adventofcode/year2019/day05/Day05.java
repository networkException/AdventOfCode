package de.jakobniklas.adventofcode.year2019.day05;

import de.jakobniklas.adventofcode.framework.Day;
import de.jakobniklas.adventofcode.year2019.day05.computer.Computer;

public class Day05 extends Day
{
    public Day05()
    {
        aPath = "res/2019/day05.txt";
    }

    @Override
    public String partA(Boolean debug)
    {
        return String.format("Diagnostic code for mode 1 is '%d'", new Computer(aPath, 1).compute(debug).get(223));
    }

    @Override
    public String partB(Boolean debug)
    {
        return String.format("Diagnostic code for mode 5 is '%d'", new Computer(aPath, 5).compute(debug).get(223));
    }
}
