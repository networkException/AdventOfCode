package de.nwex.adventofcode.year2019.day04;

import de.nwex.adventofcode.framework.Day;

public class Day04 extends Day
{
    @Override
    public String partA(Boolean debug)
    {
        return String.format("Found passwords in range '240920' - '789857': '%d'", Password.find(240920, 789857, false).size());
    }

    @Override
    public String partB(Boolean debug)
    {
        return String.format("Found passwords in range '240920' - '789857': '%d'", Password.find(240920, 789857, true).size());
    }
}
